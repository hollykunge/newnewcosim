package com.casic.cloud.toolEnvironment.util;

public interface FileModel {
	//flowCart.par文件模板
	String flowCart = "# solver params file written on Tue Feb 25 21:23:03 涓?鍥芥爣鍑嗘椂闂? 2014 by luxueling@lu-xueling\r\n\r\n"+
"begin bound_cond\r\n"+
"    begin dir_cond\r\n"+
"        bcx0 0\r\n"+
"        bcx1 0\r\n"+
"        bcy0 0\r\n"+
"        bcy1 0\r\n"+
"        bcz0 0\r\n"+
"        bcz1 0\r\n"+
"    end dir_cond\r\n"+
"end bound_cond\r\n\r\n"+

"begin case_info\r\n"+
"    Mach 5\r\n"+
"    Rho_inf 1.0\r\n"+
"    alpha 10\r\n"+
"    beta 10\r\n"+
"    gamma 1.4\r\n"+
"end case_info\r\n\r\n"+

"begin conv_hist\r\n"+
"    iForce 3\r\n"+
"    iHist 1\r\n"+
"    nOrders 6\r\n"+
"end conv_hist\r\n\r\n"+

"begin file_info\r\n"+
"    MeshFile TrialsTest_Temp_c3d.mesh.mg\r\n"+
"    MeshInfo TrialsTest_Temp_c3d.mesh.Info\r\n"+
"end file_info\r\n\r\n"+

"begin part_info\r\n"+
"    nPart 1\r\n"+
"    type 1\r\n"+
"end part_info\r\n\r\n"+

"begin solver_controls\r\n"+
"    1 {}\r\n"+
"    begin other_controls\r\n"+
"        CFL 1.4\r\n"+
"        FluxFun 0\r\n"+
"        Limiter 2\r\n"+
"        MG_cycleType 2\r\n"+
"        MG_nPost 1\r\n"+
"        MG_nPre 1\r\n"+
"        nMGlev 2\r\n"+
"        wallBCtype 0\r\n"+
"    end other_controls\r\n\r\n"+

"    begin runge_kutta\r\n"+
"        nStage 5\r\n"+
"        rk1coeff 0.0695\r\n"+
"        rk1grad 1\r\n"+
"        rk2coeff 0.1602\r\n"+
"        rk2grad 0\r\n"+
"        rk3coeff 0.2898\r\n"+
"        rk3grad 0\r\n"+
"        rk4coeff 0.5060\r\n"+
"        rk4grad 0\r\n"+
"        rk5coeff 1.0\r\n"+
"        rk5grad 0\r\n"+
"        rk6coeff 1.0\r\n"+
"        rk6grad 0\r\n"+
"        rk7coeff 1.0\r\n"+
"        rk7grad 0\r\n"+
"        rk8coeff 1.0\r\n"+
"        rk8grad 0\r\n"+
"    end runge_kutta\r\n"+
"end solver_controls\r\n";
	
	//Project_c3d.i.tri文件模板
	String project_c3d_input ="\"Cartesian Mesh Generation Input Specifications\"\r\n"+
"1. Surface Geometry File Name:\r\n"+
"	TrialsTest_Temp_c3d.i.tri\r\n\r\n"+
"2. Outer Cartesian Box Specs:\r\n"+
"	Xmin 	Xmax 	Ymin 	Ymax 	Zmin 	Zmax\r\n"+
"	-0.524997 0.875001 -0.699998 0.7 -0.699999 0.7\r\n\r\n"+
"3. Starting Mesh Dimensions:\r\n"+
"  # verts in X    # verts in Y     # verts in Z\r\n"+
"	3	3	3\r\n\r\n"+
"4. Maximum Hex Cell Aspect Ratio ( Isotropic = 1):\r\n"+
"	1\r\n\r\n"+

"5. Minimum cell refinements on surface (auto = -1):\r\n"+
"	-1\r\n\r\n"+

"6. Maximum Number of cell refinements:\r\n"+
"	10\r\n\r\n"+

"7. Num of bits of resolution (maximum = 21.):\r\n"+
"	21\r\n\r\n"+

"8. Surface Refinement Criteria: surfRef\r\n"+
"	#Comp Id	#Min. Ref	#Max. Ref\r\n"+
"	CompRef 0	0		9\r\n"+
"	CompRef 1	0		10\r\n"+
"	CompRef 2	0		10\r\n"+
"	CompRef 3	0		10\r\n"+
"	CompRef 4	0		10\r\n\r\n"+

"9. Bounday conditions (Not Required):\r\n"+
"	LoX HiX  LoY  HiY  LoZ HiZ\r\n"+
"	 0   0   0    0     0   0\r\n";
	
	String project_c3d_cntl = "# Pre-specified regions for adaptation for mesh generation.\r\n"+
"# BBox: level 	Xmin 	Xmax 	Ymin 	Ymax 	Zmin 	Zmax\r\n"+
"$__Prespecified_Adaptation_Regions:\r\n";
	
	String sanshi_bat = "\"intersect.exe\" -i TrialsTest_Temp_c3d.a.tri -o TrialsTest_Temp_c3d.i.tri -ascii -v\r\n"+

"\"cubes.exe\" -i TrialsTest_Temp_c3d.input -o TrialsTest_Temp_c3d.mesh -ascii  -b 3 -a 20  -pre TrialsTest_Temp_c3d.cntl -no_est\r\n"+ 

"\"reorder.exe\" -i TrialsTest_Temp_c3d.mesh -o TrialsTest_Temp_c3d.mesh.R  -m TrialsTest_Temp_c3d.mesh.Info -sfc H\r\n"+

"\"mgPrep.exe\" -n 2 -i TrialsTest_Temp_c3d.mesh.R -m TrialsTest_Temp_c3d.mesh.Info  -o TrialsTest_Temp_c3d.mesh.mg -sfc H\r\n"+

"\"flowCartinput.exe\" -i flowCart.par -o flowCart.cntl -f TrialsTest_Temp_c3d.fam\r\n"+

"\"flowCart.exe\" -N 100 -i flowCart.cntl -clic -his -T -icem -ncpus 2\r\n"+

"\"clic.exe\" -i TrialsTest_Temp_c3d.clic.cntl > TrialsTest_Temp_c3d.clic.out -ascii -v\r\n"+

"\"ComResult.exe\" TrialsTest_Temp beta alpha Mach";
	

	
}
