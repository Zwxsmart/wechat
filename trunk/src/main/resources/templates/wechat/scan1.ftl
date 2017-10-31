<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<html>
<head>
    <head>
        <title>Title</title>
        <script type="text/javascript" src="jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="jquery.qrcode.js"></script>
        <script>
            //绑定微信登陆

            $(function() {
//              var url = "http://www.iqidi.com/H5/BindWechat?id=1111";
//              url = encodeURIComponent(url);
//              alert(url)
//              document.getElementById("#imgQRcode").attr("src", "/H5/QR?url=" + url);        //打开绑定窗口
//              document.getElementById("#pWechat").dialog('open').dialog('setTitle', '使用微信扫码进行绑定');
                        $("#imgQRcode").qrcode({
                            render: "table",
                            width: 200,
                            height:200,
                            text:toUtf8("我的")
                        });
                    }
            )

            function toUtf8(str) {
                var out, i, len, c;
                out = "";
                len = str.length;
                for(i = 0; i < len; i++) {
                    c = str.charCodeAt(i);
                    if ((c >= 0x0001) && (c <= 0x007F)) {
                        out += str.charAt(i);
                    } else if (c > 0x07FF) {
                        out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                        out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));
                        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
                    } else {
                        out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));
                        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
                    }
                }
                return out;
            }
        </script>
    </head>
<body>
<div id="imgQRcode"></div>
</body>
</html>
