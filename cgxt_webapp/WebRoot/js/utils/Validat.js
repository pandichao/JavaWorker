$(function(){    
    /*************************可直接调用的校验方法***************************/    
    /* 
    notNull('age','年龄不能为空');   
    reapet('password','repassword','两次输入不相同');   
    number('age','只能为数字');   
    cellPhone('phone','手机号格式不正确');   
    phone('phone','电话号码格式不正确');   
    email('email','邮箱格式不正确');   
    unique('username','unique.html','name');   
    form('form','user_regist.html');   
    */  
    //不为空函数    
    notNull = function(id, msg){    
        $('#'+id).validatebox({    
            required: true,    
            missingMessage: msg    
        });    
    }    
    //重复函数    
    reapet = function(id, re_id, msg){    
        id = '#'+id;    
        $('#'+re_id).validatebox({    
            validType: "reapet['"+id+"','"+msg+"']"    
        });    
    };    
    //数字    
    number = function(id, msg){    
        $('#'+id).validatebox({    
            validType: 'number["'+msg+'"]'    
        });    
    };    
    //手机号码    
    cellPhone = function(id, msg){    
        $('#'+id).validatebox({    
            validType: 'cellPhone["'+msg+'"]'    
        });    
    };    
    //电话号码    
    phone = function(id, msg){    
        $('#'+id).validatebox({    
            validType: 'phone["'+msg+'"]'    
        });    
    };    
    //邮箱    
    email = function(id,msg){    
        $('#'+id).validatebox({    
            validType: 'email',    
            invalidMessage: msg    
        });    
    };    
    //url    
    url = function(id, msg){    
        $('#'+id).validatebox({    
            validType: 'url',    
            invalidMessage: msg    
        });    
    };    
    //ip    
    ip = function(id, msg){    
        $('#'+id).validatebox({    
            validType: 'ip["'+msg+'"]'    
        });    
    };    
    /**  
     * 提交后台进行唯一性校验  
     * @param id:校验元素的id,url: 提交的地址,paramName: 传入值的参数名称  
     */    
    unique = function(id, url, paramName){    
        $('#'+id).validatebox({    
            validType: 'unique["'+url+'","'+id+'","'+paramName+'"]'    
        });    
    };    
    //提交，数据无效时阻止提交    
    form = function(id, url){    
         $("#"+id).form({    
            url: url,    
            onSubmit: function(){    
                return $(this).form('validate');    
            },    
            success: function(data){    
                alert(data);    
            }    
       });    
    };    
    /*************************不为空校验 required="true"********************************/    
    //在HMTL标签中加入required="true"可进行不能为空校验    
    $("*").each(function(){    
        if($(this).attr('required')){    
            $(this).validatebox({    
                required: true,    
                missingMessage: '不能为空'    
            });    
        }    
    });    
    //当使用struts标签时，加入属性required="true"能过下面代码实现不能为空校验    
    //注意:struts标签需用label     
    $('span').each(function(){    
        //遍历所有span标签，检验是否设有class="required"    
        if($(this).attr('class')=='required'){    
            $("#"+$(this).parent().attr('for')).validatebox({    
                required: true,    
                missingMessage: '不能为空'    
            });    
        }    
    });    
        
    /*************************自定义方法********************************/    
    /**  
     *自定义的校验方法(校验两次密码是否相同)  
     * @param param为传入第一次输入的密码框的id  
     * @call repeat['#id']  
     */    
    $.extend($.fn.validatebox.defaults.rules,{    
        reapet: {    
            validator: function(value, param){    
                var pwd = $(param[0]).attr('value');     
                if(pwd != value){    
                    return false;    
                }    
                return true;    
            },    
            message: '{1}'    
        }    
    });    
            
    //利用正则进行数字校验    
    $.extend($.fn.validatebox.defaults.rules, {    
        number: {    
        validator: function(value, param){    
           return /^-?(?:/d+|/d{1,3}(?:,/d{3})+)(?:/./d+)?$/.test(value);    
       },    
        message: '{0}'    
    }    
    });    
    //手机号    
    $.extend($.fn.validatebox.defaults.rules,{    
        cellPhone: {    
        validator: function(value, param){    
                return /^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[7-8])[0-9]{8}$/.test(value);    
            },    
        message: '{0}'    
        }    
    });    
    /**  
     * 电话号码  
     * 匹配格式：11位手机号码  
     * 3-4位区号，7-8位直播号码，1－4位分机号  
     * 如：12345678901、1234-12345678-1234  
     */    
    $.extend($.fn.validatebox.defaults.rules,{    
        phone: {    
        validator: function(value, param){    
                return /(/d{11})|^((/d{7,8})|(/d{4}|/d{3})-(/d{7,8})|(/d{4}|/d{3})-(/d{7,8})-(/d{4}|/d{3}|/d{2}|/d{1})|(/d{7,8})-(/d{4}|/d{3}|/d{2}|/d{1}))$/.test(value);    
            },    
        message: '{0}'    
        }    
    });    
    //ip校验    
    $.extend($.fn.validatebox.defaults.rules,{    
        ip: {    
        validator: function(value, param){    
                return /^((2[0-4]/d|25[0-5]|[01]?/d/d?)/.){3}(2[0-4]/d|25[0-5]|[01]?/d/d?)$/.test(value);    
            },    
        message: '{0}'    
        }    
    });    
    //唯一性校验    
    $.extend($.fn.validatebox.defaults.rules,{    
        unique: {    
            validator: function(value, param){    
                value = $('#'+param[1]).attr('value');    
                $('#'+param[1]).load(param[0]+"?"+param[2]+"="+value,    
                function(responseText, textStatus, XMLHttpRequest){    
                    if(responseText) //后台返回true或者false    
                        return true;        
                });    
                return false;    
            },    
            message: '用户名已存在'    
        }    
    });    
});    