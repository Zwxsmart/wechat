window.onload = function() {


	$.getJSON("/share?type=P00210119", function(data) {
		var appid = data.appid;
		var nonceStr = data.nonceStr;
		var signature = data.signature;
		var timestamp = data.timestamp;
		wx.config({
			debug: false,
			appId: appid,
			timestamp: timestamp,
			nonceStr: nonceStr,
			signature: signature,
			jsApiList: [
				'checkJsApi',
				'scanQRCode',

			]
		});

	})
	wx.ready(function() {
		// 1 判断当前版本是否支持指定 JS 接口，支持批量判断
		wx.checkJsApi({
			jsApiList: [
				'getNetworkType',
				'previewImage'
			],
			success: function(res) {}
		});
		wx.scanQRCode({
			needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			success: function(res) {
				var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				var str = result.split(',')
				window.location.href = "http://www.baodu.com?userNo="+str[1];

			}
		});
	})

}