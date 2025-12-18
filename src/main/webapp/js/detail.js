$(function() {

    MagnifierF("MagnifierWrap2")

    layui.use(['layer'], function() {
        var layer = layui.layer

        //联系卖家
        $('.contect-seller').click(function() {
            // $('.user-contect').show()
            layer.open({
                type: 1,
                shade: false,
                title: false, //不显示标题
                content: $('.get-contect'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                cancel: function() {
                    layer.closeAll()
                    $('.get-contect').hide()
                }
            });
        })

        //点击购买闲置
        $('.buy-shop-btn').click(function() {
            // $('.user-contect').show()
            layer.open({
                type: 1,
                shade: false,
                title: false, //不显示标题
                content: $('.goods-buy'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                cancel: function() {
                    layer.closeAll()
                    $('.goods-buy').hide()
                }
            });
        })

        //收藏
        $('.want').click(function() {
            if ($('.want i').hasClass('wanted')) {
                return layer.msg('您已经收藏过该闲置了哦~')
            }
            var url = '/user/collect'
            var Data = {
                gid: $(this).attr('data-id')
            }
            Common.ajax(url, Data, function(data) {
                if (data.success) {
                    layer.msg(data.msg)
                    $('.want i').addClass('wanted')
                }
            }, function(err) {
                layer.msg(data.msg)
                console.log(err)
            }, function() {}, true, 'POST')
        })

        //举报
        $('.report').click(function() {
            layer.open({
                type: 1,
                title: '举报提交',
                skin: 'report-layer', //加上边框
                area: ['420px', '300px'], //宽高
                content: '<p class="r-title">举报 “' + $('.good-info h2').html() + '” </p>' +
                    '<textarea class="description" cols="30" rows="10" placeholder="举报描述"></textarea>' +
                    '<p><a href="javascript:;" class="layui-btn layui-btn-normal submit-report">提交</a></p>'
            })

            $('.submit-report').click(function() {
                if ($('.description').val() == '' || $('.description').val() == undefined) {
                    return layer.msg('请输入举报描述')
                }
                if ($('.description').val().length > 100 || $('.description').val().length < 10) {
                    return layer.msg('举报描述字数必须在10~100字之间！')
                }
                var url = '/publish/report'
                var Data = {
                    gid: $('.want').attr('data-id'),
                    good_title: $('.good-info h2').html(),
                    description: $('.description').val()
                }
                Common.ajax(url, Data, function(data) {
                    if(data.success){
                        layer.msg(data.msg)
                    }
                }, function(err) {
                    console.log(err)
                }, function() {}, true, 'POST')
            })
        })

        //加载评论列表
        function loadComments() {
            var gid = $('.want').attr('data-id')
            $.ajax({
                url: '/publish/comment/list?gid=' + gid,
                type: 'GET',
                dataType: 'json',
                success: function(res) {
                    if (res.success && res.data) {
                        var html = ''
                        res.data.forEach(function(comment) {
                            html += '<li class="comment-item" data-id="' + comment.id + '" style="margin-bottom: 20px; padding: 12px; border: 1px solid #eee; border-radius: 6px; background: #fafafa;">'
                            html += '  <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">'
                            html += '    <strong style="color: #333;">' + (comment.username || '匿名用户') + '</strong>'
                            html += '    <span style="color: #999; font-size: 12px;">' + comment.createAt + '</span>'
                            html += '  </div>'
                            html += '  <p style="margin: 8px 0; color: #333; line-height: 1.6;">' + comment.content + '</p>'
                            
                            // 回复列表
                            if (comment.replies && comment.replies.length > 0) {
                                html += '  <div class="reply-list" style="margin: 10px 0 0 20px; padding: 8px; background: #fff; border-left: 3px solid #2563eb; border-radius: 4px;">'
                                comment.replies.forEach(function(reply) {
                                    html += '    <div style="margin-bottom: 8px; font-size: 13px; color: #555;">'
                                    html += '      <strong>' + (reply.username || '匿名') + '</strong> 回复 '
                                    html += '      <strong>' + (reply.atUsername || '楼主') + '</strong>: '
                                    html += '      <span style="color: #333;">' + reply.content + '</span>'
                                    html += '      <span style="color: #999; font-size: 11px; margin-left: 8px;">' + reply.createAt + '</span>'
                                    html += '    </div>'
                                })
                                html += '  </div>'
                            }
                            
                            // 回复按钮
                            html += '  <div style="margin-top: 10px; text-align: right;">'
                            html += '    <a href="javascript:;" class="reply-btn" data-comment-id="' + comment.id + '" data-user-id="' + comment.userId + '" data-username="' + (comment.username || '匿名用户') + '" style="color: #2563eb; font-size: 13px; text-decoration: none;">回复</a>'
                            html += '  </div>'
                            html += '</li>'
                        })
                        $('#comment-list-container').html(html || '<li style="color: #999; padding: 10px;">暂无评论</li>')
                        
                        // 绑定回复按钮事件
                        $('.reply-btn').off('click').on('click', function() {
                            var commentId = $(this).data('comment-id')
                            var atUserId = $(this).data('user-id')
                            var atUsername = $(this).data('username')
                            
                            layer.prompt({
                                title: '回复 ' + atUsername,
                                formType: 2,
                                maxlength: 100,
                                area: ['400px', '150px']
                            }, function(value, index, elem) {
                                if (!value || value.trim().length === 0) {
                                    return layer.msg('回复内容不能为空')
                                }
                                if (value.length > 100) {
                                    return layer.msg('回复内容不能超过100字')
                                }
                                
                                $.ajax({
                                    url: '/publish/reply/add',
                                    type: 'POST',
                                    data: {
                                        commentId: commentId,
                                        atUserId: atUserId,
                                        content: value
                                    },
                                    dataType: 'json',
                                    success: function(res) {
                                        if (res.success) {
                                            layer.msg(res.msg)
                                            layer.close(index)
                                            loadComments()  // 刷新评论列表
                                        } else {
                                            layer.msg(res.msg)
                                        }
                                    },
                                    error: function() {
                                        layer.msg('回复失败，请重试')
                                    }
                                })
                            })
                        })
                    }
                }
            })
        }

        // 页面加载时先加载评论列表
        loadComments()

        //评论
        $('.comments').on('input', function(e) {
            let words_number = $('.comments').val().length
            if (words_number > 100) {
                $('.comments-words').html('<span style="color: red;">' + words_number + '</span>' + '/100')
            } else {
                $('.comments-words').html(words_number + '/100')
            }

        })

        $('.comment-submit').click(function() {
            let comment = {
                gid: $('.want').attr('data-id'),
                content: $('.comments').val()
            }

            if (comment.content.length > 100) {
                return layer.msg('评论内容不得超过100字！')
            }

            if (comment.content.trim().length === 0) {
                return layer.msg('评论内容不能为空！')
            }

            let url = '/publish/comment/add'
            Common.ajax(url, comment, function(data) {
                if (data.success) {
                    layer.msg(data.msg)
                    $('.comments').val('')
                    $('.comments-words').html('0/100')
                    loadComments()  // 刷新评论列表
                } else {
                    layer.msg(data.msg)
                }
            }, function(err) {
                layer.msg('提交失败，请重试')
                console.log(err)
            }, function() {}, true, 'POST')
        })


    })

})