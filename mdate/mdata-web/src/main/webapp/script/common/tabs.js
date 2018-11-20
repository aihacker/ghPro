(function () {

    function notifyFrameFirstShow($frame) {
        var frameWindow = $frame[0].contentWindow;
        if(!frameWindow || frameWindow.location.href === 'about:blank' ||
            frameWindow.document.readyState !== 'complete') {
            var notifyFirstShowTime0 = $frame.data('notifyFirstShowTime0');
            if(new Date().getTime() - notifyFirstShowTime0 < 20 * 1000) {
                setTimeout(function () {
                    notifyFrameFirstShow($frame);
                }, 10);
            }
        }
        else {
            frameWindow.notifyFirstShow && frameWindow.notifyFirstShow();
        }
    }

    function init() {
        var $tabs = $('.g-tabs');
        if($tabs.length) {
            var $header = $tabs.find('.header');
            var $content = $tabs.find('.content');
            $header.on('click', 'a', function () {
                var $a = $(this);
                $header.find('.active').removeClass('active');
                var $li = $a.closest('li');
                $li.addClass('active');
                var index = $li.index();
                var $sheets = $content.children('.sheet');

                // var $activeFrame = $sheets.filter(':visible').find('iframe');
                // if($activeFrame)

                $sheets.hide();
                var $sheet = $sheets.eq(index);
                $sheet.show();
                $(window).trigger('resize');

                if(!$sheet.data('firstShow')) {
                    $sheet.data('firstShow', true);

                    var $iframe = $sheet.find('iframe');
                    if($iframe.length) {
                        $iframe.data('notifyFirstShowTime0', new Date().getTime());
                        notifyFrameFirstShow($iframe);
                    }
                }
            });
        }
    }


    $(function () {
        init();
    });

})();