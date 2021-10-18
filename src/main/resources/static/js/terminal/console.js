$(document).ready(function(){
    let consoleSocket = null;
    let _ = {
        divBgColor: function (clazz, color) {
            $('.' + clazz).css({
                backgroundColor: color,
                color: color + ' 0px 0px 5px'
            });
        },
        divHeight: function (id) {
            $('#'+id).css({height: document.documentElement.clientHeight -  50 + 'px'});
        },
        hasClass: function (id, className) {
            return $('#' + id).hasClass(className);
        },
        scrollTop: function (id) {
            $("#"+id).scrollTop($("#"+id)[0].scrollHeight);
        }
    }
    _.divHeight('log');
    $("#startConsole").bind("click", function() {
        if (consoleSocket != null) {
            consoleSocket.close();
            consoleSocket = null;
        }
        let ws = window.location.origin + "/console";
        ws = ws.replace("http", "ws");
        ws = ws.replace("https", "wss");
        consoleSocket = new ReconnectingWebSocket(ws);
        consoleSocket.onopen = function() {
            _.divBgColor('divCenter', 'green');
            Qmsg.success('console open');
        };
        consoleSocket.onmessage = function(event) {
            let log = event.data;
            if (log.indexOf('\t') == 0) {
                $('#log').html($('#log').html() + '&nbsp;&nbsp;&nbsp;&nbsp;' + log + '<br/>');
            } else {
                $('#log').html($('#log').html() + log + '<br/>');
            }

            if ($($('#screenScroll').children('span').get(0)).hasClass('fa-toggle-on')) {
                _.scrollTop('log');
            }
        };
        consoleSocket.onclose = function() {
            _.divBgColor('divCenter', '#a7a7a1');
            Qmsg.success('console close');
        };
        consoleSocket.onerror = function() {
            Qmsg.error('console error');
        }
    });
    $("#cleanScreen").bind("click", function() {
        $('#log').html('');
    });
    $("#stopConsole").bind("click", function() {
        if (consoleSocket != null) {
            consoleSocket.close();
            consoleSocket = null;
        }
    });
    $("#qSwitch").bind("click", function() {
        if (_.hasClass('qSwitch', 'na-switch-on')) {
            $('#qSwitch').removeClass('na-switch-on');
        } else {
            $('#qSwitch').addClass('na-switch-on');
            _.scrollTop('log');
        }
    });
    $("#screenScroll").bind("click", function() {
        let ele = $(this).children('span').get(0);
        if ($(ele).hasClass('fa-toggle-off')) {
            $(ele).removeClass('fa-toggle-off');
            $(ele).addClass('fa-toggle-on');
            $(ele).attr('title', 'unscroll');
            _.scrollTop('log');
        } else {
            $(ele).removeClass('fa-toggle-on');
            $(ele).addClass('fa-toggle-off');
            $(ele).attr('title', 'scroll');
        }
    });
})
$(window).resize(function() {
    $('#log').css({height: document.documentElement.clientHeight -  50 + 'px'});
});