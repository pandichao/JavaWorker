$(function(){    
    /*************************��ֱ�ӵ��õ�У�鷽��***************************/    
    /* 
    notNull('age','���䲻��Ϊ��');   
    reapet('password','repassword','�������벻��ͬ');   
    number('age','ֻ��Ϊ����');   
    cellPhone('phone','�ֻ��Ÿ�ʽ����ȷ');   
    phone('phone','�绰�����ʽ����ȷ');   
    email('email','�����ʽ����ȷ');   
    unique('username','unique.html','name');   
    form('form','user_regist.html');   
    */  
    //��Ϊ�պ���    
    notNull = function(id, msg){    
        $('#'+id).validatebox({    
            required: true,    
            missingMessage: msg    
        });    
    }    
    //�ظ�����    
    reapet = function(id, re_id, msg){    
        id = '#'+id;    
        $('#'+re_id).validatebox({    
            validType: "reapet['"+id+"','"+msg+"']"    
        });    
    };    
    //����    
    number = function(id, msg){    
        $('#'+id).validatebox({    
            validType: 'number["'+msg+'"]'    
        });    
    };    
    //�ֻ�����    
    cellPhone = function(id, msg){    
        $('#'+id).validatebox({    
            validType: 'cellPhone["'+msg+'"]'    
        });    
    };    
    //�绰����    
    phone = function(id, msg){    
        $('#'+id).validatebox({    
            validType: 'phone["'+msg+'"]'    
        });    
    };    
    //����    
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
     * �ύ��̨����Ψһ��У��  
     * @param id:У��Ԫ�ص�id,url: �ύ�ĵ�ַ,paramName: ����ֵ�Ĳ�������  
     */    
    unique = function(id, url, paramName){    
        $('#'+id).validatebox({    
            validType: 'unique["'+url+'","'+id+'","'+paramName+'"]'    
        });    
    };    
    //�ύ��������Чʱ��ֹ�ύ    
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
    /*************************��Ϊ��У�� required="true"********************************/    
    //��HMTL��ǩ�м���required="true"�ɽ��в���Ϊ��У��    
    $("*").each(function(){    
        if($(this).attr('required')){    
            $(this).validatebox({    
                required: true,    
                missingMessage: '����Ϊ��'    
            });    
        }    
    });    
    //��ʹ��struts��ǩʱ����������required="true"�ܹ��������ʵ�ֲ���Ϊ��У��    
    //ע��:struts��ǩ����label     
    $('span').each(function(){    
        //��������span��ǩ�������Ƿ�����class="required"    
        if($(this).attr('class')=='required'){    
            $("#"+$(this).parent().attr('for')).validatebox({    
                required: true,    
                missingMessage: '����Ϊ��'    
            });    
        }    
    });    
        
    /*************************�Զ��巽��********************************/    
    /**  
     *�Զ����У�鷽��(У�����������Ƿ���ͬ)  
     * @param paramΪ�����һ�������������id  
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
            
    //���������������У��    
    $.extend($.fn.validatebox.defaults.rules, {    
        number: {    
        validator: function(value, param){    
           return /^-?(?:/d+|/d{1,3}(?:,/d{3})+)(?:/./d+)?$/.test(value);    
       },    
        message: '{0}'    
    }    
    });    
    //�ֻ���    
    $.extend($.fn.validatebox.defaults.rules,{    
        cellPhone: {    
        validator: function(value, param){    
                return /^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[7-8])[0-9]{8}$/.test(value);    
            },    
        message: '{0}'    
        }    
    });    
    /**  
     * �绰����  
     * ƥ���ʽ��11λ�ֻ�����  
     * 3-4λ���ţ�7-8λֱ�����룬1��4λ�ֻ���  
     * �磺12345678901��1234-12345678-1234  
     */    
    $.extend($.fn.validatebox.defaults.rules,{    
        phone: {    
        validator: function(value, param){    
                return /(/d{11})|^((/d{7,8})|(/d{4}|/d{3})-(/d{7,8})|(/d{4}|/d{3})-(/d{7,8})-(/d{4}|/d{3}|/d{2}|/d{1})|(/d{7,8})-(/d{4}|/d{3}|/d{2}|/d{1}))$/.test(value);    
            },    
        message: '{0}'    
        }    
    });    
    //ipУ��    
    $.extend($.fn.validatebox.defaults.rules,{    
        ip: {    
        validator: function(value, param){    
                return /^((2[0-4]/d|25[0-5]|[01]?/d/d?)/.){3}(2[0-4]/d|25[0-5]|[01]?/d/d?)$/.test(value);    
            },    
        message: '{0}'    
        }    
    });    
    //Ψһ��У��    
    $.extend($.fn.validatebox.defaults.rules,{    
        unique: {    
            validator: function(value, param){    
                value = $('#'+param[1]).attr('value');    
                $('#'+param[1]).load(param[0]+"?"+param[2]+"="+value,    
                function(responseText, textStatus, XMLHttpRequest){    
                    if(responseText) //��̨����true����false    
                        return true;        
                });    
                return false;    
            },    
            message: '�û����Ѵ���'    
        }    
    });    
});    