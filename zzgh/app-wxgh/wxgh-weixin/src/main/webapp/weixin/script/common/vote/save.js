/**
 * Created by Admin on 2016/7/8.
 */
(function () {
    var $span;
    var $li;
    var loginUserId;
    var loginUserName;

    var saveLoading = null;

    $(function () {
        loginUserId = $('#loginUserId').val();
        loginUserName = $('#loginUserName').val();

        var vote_count = 2;//最少选项

        $('.votedel').click(function () {
            $(this).parent().remove();
        });

        $('#addchoice').click(function () {
            vote_count++;

            $span = $('<span class="votedel" title="删除"></span>');

            $li = $('<li class="votechoice">' +
            '<span>选项</span><input type="text" name="choice" placeholder=""/></li>');

            $li.find('input').attr("placeholder", vote_count);

            $span.click(function () {
                var ul = $(this).parent();
                $($(this).parent().nextAll("li")).each(function () {
                    var p = $(this).find('input').attr("placeholder");
                    $(this).find("input").eq(0).attr("placeholder", p - 1);
                });
                ul.remove();
                vote_count--;

            });

            $li.append($span);

            $li.insertBefore(".addchoice");
        });

        $('#adminBtn').click(release);
    });


    function release() {

        if (saveLoading == null) {
            saveLoading = new ui.loading("发布中，请稍候...");
        }
        saveLoading.show();

        var url = homeHttpPath + "/wx/common/vote/save/add_voted.json";
        var theme = $('.textarea_content').val();
        var list = document.getElementsByName("choice");

        var voted = {
            // action: 'AddVoted',
            theme: theme,
            userid: loginUserId,
            userName: loginUserName
        };
        $.ajax({
            url: url,
            data: voted,
            dataType: 'json',
            type: 'post',
            async: true,
            success: function (data) {
                for (var i = 0; i < list.length; i++) {
                    var voteOption = {
                        action: 'AddVoteOption',
                        options: list[i].value
                    };
                    $.ajax({
                        url: url,
                        data: voteOption,
                        dataType: 'json',
                        type: 'post',
                        async: true,
                        success: function (data) {
                            saveLoading.hide();
                            window.location.href = homeHttpPath + "/wx/common/vote/index.html";
                        }
                    });

                }
            }
        });
    }

})();