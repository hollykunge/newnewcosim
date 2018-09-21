/**
 * Created by d on 2017/4/13.
 */
var wizard = {};
wizard.bs = {};

wizard.bs.bstrooptions = {
    width: '500px',
    html: 'true',
    nbtext: '下一步',
    place: 'bottom',
    title: '提示',
    content: 'content'
};
wizard.bs.bstroinit = function (selector, options, step) {
    if (selector) {
        var $element = $(selector);
        if ($element.length > 0) {
            var opt = $.extend({}, wizard.bs.bstrooptions, options);
            if (typeof options == 'string') {
                opt.content = options;
            } else {
                $.extend(opt, options);
            }

            $element.each(function () {
                $(this).attr({
                    'data-bootstro-width': opt.width,
                    'data-bootstro-title': opt.title,
                    'data-bootstro-html': opt.html,
                    'data-bootstro-content': opt.content,
                    'data-bootstro-placement': opt.place,
                    'data-bootstro-nextButtonText': opt.nbtext,
                    'data-bootstro-step': step
                }).addClass('bootstro');
            });
        }
    }
};
wizard.bs.bstroopts = {
    prevButtonText: '上一步',
    finishButton: '<button class="btn btn-lg btn-success bootstro-finish-btn">完成</button>',
    stopOnBackdropClick: false,
    stopOnEsc: false
};
wizard.bs.bstro = function (bss, options) {
    if (bss && bss.length > 0) {
        for (var i = 0; i < bss.length; i++) {
            wizard.bs.bstroinit(bss[i][0], bss[i][1], i);
        }

        var opt = $.extend({}, wizard.bs.bstroopts);
        if (options) {
            if (options.hasOwnProperty('pbtn')) {
                opt.prevButtonText = options.pbtn;
            }
            if (options.hasOwnProperty('obtn')) {
                if (options.obtn == '') {
                    opt.finishButton = '';
                } else {
                    opt.finishButton = '<button class="btn btn-mini btn-success bootstro-finish-btn">' + options.obtn + '</button>';
                }
                opt.onExit = options.exit;
            }
            if (options.hasOwnProperty('stop')) {
                opt.stopOnBackdropClick = options.stop;
                opt.stopOnEsc = options.stop;
            }
        }

        bootstro.start('.bootstro', opt);
    }
};