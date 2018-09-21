package com.hotent.platform.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.model.system.SysParam;

/**
 * 将json数据:
 * [{"type":2,"typeName":"And","children":[{"type":3,"expression":"age>30","dataType":"Integer"},{"type":3,"expression":"age<33","dataType":"Integer"}]},]
 * 解析为sql参数查询数据，并根据集合关系合并最终结果。
 * 算法解译：
 * 1.取得集合关系：AND,OR,表达式。
 * 		1.1如果为AND或OR
 * 			1.1.1判断AND或OR节点下有没有表达式，即有没有children节点。
 *				如果有children，即将children下的表达式查询数据库并按照AND，OR关系合并数据，数据结果存入linkedlist里。
 *				如果没有children，即直接将AND，OR关系直接存入linkedlist中。
 * 		1.2如果为表达式，直接将表达式数询数据库，并将结果存入linkedlist中。
 * 
 * 2.处理linklist中的结果。
 * 	 按先后顺序将linkedlist 中的没有children节点的AND,OR关系前后的表达式或有children节点的数据合并。
 * 
 * 
 * 
 */

public abstract class ParamSearch<T> {
	/**
	 * 查询数据库的纯虚方法
	 * @param property
	 * @return
	 */
	public abstract List<T> getFromDataBase(Map<String,String> property);

	/**
	 *	 将json数据:
	 * [{"type":2,"typeName":"And","children":[{"type":3,"expression":"age>30","dataType":"Integer"},{"type":3,"expression":"age<33","dataType":"Integer"}]},]
	 * 解析为sql参数查询数据，并根据集合关系合并最终结果。
	 * 算法解译：
	 * 1.取得集合关系：AND,OR,表达式。
	 * 		1.1如果为AND或OR
	 * 			1.1.1判断AND或OR节点下有没有表达式，即有没有children节点。
	 *				如果有children，即将children下的表达式查询数据库并按照AND，OR关系合并数据，数据结果存入linkedlist里。
	 *				如果没有children，即直接将AND，OR关系直接存入linkedlist中。
	 * 		1.2如果为表达式，直接将表达式数询数据库，并将结果存入linkedlist中。
	 * 
	 * 2.处理linklist中的结果。
	 * 	 按先后顺序将linkedlist 中的没有children节点的AND,OR关系前后的表达式或有children节点的数据合并。
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public List<T> getByParam(String json) throws Exception{
		//将json字串符转为json对像。
		if(json==null||json.equals(""))return null;
		JSONArray ja= JSONArray.fromObject(json);
		List<Map> ml= (List) JSONArray.toCollection(ja, Map.class);
		if(BeanUtils.isEmpty(ml))return null;
		//存储数数据的有序容器。
		LinkedList<ParamResult> resultList =new LinkedList<ParamResult>();
		//循环json对像
		for(int i=0;i<ml.size();i++){
			Map m=ml.get(i);
			int type=(Integer) m.get("type");
			String typeName=(String) m.get("typeName");
			//集合运算类
			ParamResult<T> res=new ParamResult<T>(type, typeName);
			//判断集合关系
			switch(type){
			case SysParam.CONDITION_OR://或
				ArrayList<net.sf.ezmorph.bean.MorphDynaBean> children1 =(ArrayList<net.sf.ezmorph.bean.MorphDynaBean>) m.get("children");
				if(BeanUtils.isNotEmpty(children1)){
					for(net.sf.ezmorph.bean.MorphDynaBean c1:children1){
						String expression =(String) c1.get("expression");
						String dataType=(String) c1.get("dataType");
						//将json属性转化为可用于sql查询是的参数
						Map<String,String> property=handlerParam(expression,dataType);
						//查询数据库
						List<T> ul=getFromDataBase(property);
						//将或下面的据库合部交结集合运算类处理
						res.add(expression,ul);
					}
				}
				break;
			case SysParam.CONDITION_AND://与
				ArrayList<net.sf.ezmorph.bean.MorphDynaBean> children2 =(ArrayList<net.sf.ezmorph.bean.MorphDynaBean>) m.get("children");
				if(BeanUtils.isNotEmpty(children2)){
					
					for(net.sf.ezmorph.bean.MorphDynaBean c2:children2){
						String expression =(String) c2.get("expression");
						String dataType=(String) c2.get("dataType");
						//将json属性转化为可用于sql查询是的参数
						Map<String,String> property=handlerParam(expression,dataType);
						//查询数据库
						List<T> ul=getFromDataBase(property);
						//将与下面的据库合部交结集合运算类处理
						res.add(expression,ul);
					}
				}
				break;
			case SysParam.CONDITION_EXP://表达式
				String expression =(String) m.get("expression");
				String dataType=(String) m.get("dataType");
				//将json属性转化为可用于sql查询是的参数
				Map<String,String> property=handlerParam(expression,dataType);
				//查询数据库
				List<T> ul=getFromDataBase(property);
				//将表达式下面的据库合部交结集合运算类处理
				res.add(expression,ul);
				break;
			}
			resultList.addLast(res);
		}
		//按先后顺序将linkedlist 中的没有children节点的AND,OR关系前后的表达式或有children节点的数据合并。
		if(BeanUtils.isEmpty(resultList))return null;
		if(resultList.size()%2==0)throw new IllegalArgumentException("表达式逻辑错误");
		
		if(resultList.size()>=3){
			while(resultList.size()>1){
				ParamResult cur=resultList.removeFirst();//数据集合
				ParamResult mid=resultList.removeFirst();//and,or关系
				ParamResult nex=resultList.removeFirst();//数据集合
				if(cur!=null&&mid!=null&&nex!=null&&mid.getType()!=3&&BeanUtils.isEmpty(mid.getUserList())){
					ParamResult count=new ParamResult(mid.getType(),mid.getTypeName());
					//根据集合关系，将集合交由集合运算类处理
					count.add("cur",cur.getUserList());
					count.add("nex",nex.getUserList());
					//加入数据容器
					resultList.addFirst(count);
				}else {
					throw new IllegalArgumentException("表达式逻辑错误");
				}
			}
		}
		
		//将数据空器中的数居返回
		List<T> returnList=new ArrayList<T>();
		if(resultList!=null&&resultList.size()>0){
			for(ParamResult res:resultList){
				if(res.getUserList()!=null)
				returnList.addAll(res.getUserList());
			}
		}
		return returnList;
	}
	/**
	 * 将
	 * p>v将化为 aramKey=p and paramValue>v
	 * p=v将化为 aramKey=p and paramValue=v
	 * p<v将化为 aramKey=p and paramValue<v
	 * p!=v将化为 aramKey=p and paramValue!=v
	 * @param expression
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	protected  Map<String,String> handlerParam(String expression,String dataType) throws Exception{
		if(expression==null)return null;
		int m=-1;
		//查找关系式 "=","<",">","!=",">=","<=","like"
		String condition=null;
		for(Map.Entry<String, String> ent:SysParam.CONDITION_US.entrySet()){
			condition=ent.getKey();
			m=expression.indexOf(condition);
			if(m<0){
				condition=ent.getValue();
				m=expression.indexOf(condition);
			}
			if(m>-1)break;
		}
		if(m<0)return null;
		
		//查询paramKey,paramValue
		String tem[]=expression.split(condition);
		if(tem.length==2){
			String paramKey=tem[0].trim();
			String paramValue=tem[1].trim();
			String paramValueColumn=null;
			//根据不同的dataType，查询不同的字段
			paramValueColumn=SysParam.DATA_COLUMN_MAP.get(dataType);
			if(paramValueColumn==null)paramValueColumn="paramValue";
			Map<String,String> param=new HashMap<String,String>();
			param.put("paramKey", paramKey);
			param.put("condition", condition);
			param.put("paramValueColumn", paramValueColumn);
			if(condition=="like"||condition=="LIKE")
				param.put("paramValue", "%"+paramValue+"%");
			else
				param.put("paramValue", paramValue);
			System.out.print("[@_@]"+param.toString());
			return param;
		}else {
			throw new Exception("sql参数不是xxx"+condition+"x形式:"+expression);
		}
	}

	
	
}
