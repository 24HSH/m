// Initialize your app
var myApp = new Framework7({
			animateNavBackIcon : true,
			animatePages : Framework7.prototype.device.ios,
			pushState : true,
			swipePanel : 'left',
			modalButtonOk : '确认',
			modalButtonCancel : '取消',
			imagesLazyLoadPlaceholder : imgUrl + '/image/loading.png',
			// Hide and show indicator during ajax requests
			onAjaxStart : function(xhr) {
				myApp.showIndicator();
			},
			onAjaxComplete : function(xhr) {
				myApp.hideIndicator();
			}
		});

// Export selectors engine
var $$ = Dom7;

// Add view
var mainView = myApp.addView('.view-main', {
			// Because we use fixed-through navbar we can enable dynamic navbar
			dynamicNavbar : true
		});

// ==============================

$$('.popup.popup-region ul li').on('click', function() {
			if (!$$(this).hasClass('curr')) {
				$$('.popup.popup-region .curr').removeClass('curr');
				$$(this).addClass('curr');
				$$('.x2 .open-popup').html($$(this).data('city'));
			}
			myApp.closeModal($$('.popup.popup-region'));
		});

// ==============================

function address_index_getLocation() {
	wx.getLocation({
				type : 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
				success : function(res) {
					var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
					var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
					var speed = res.speed; // 速度，以米/每秒计
					var accuracy = res.accuracy; // 位置精度

					wx.openLocation({
								latitude : latitude, // 纬度，浮点数，范围为90 ~ -90
								longitude : longitude, // 经度，浮点数，范围为180 ~ -180。
								name : '', // 位置名
								address : '', // 地址详情说明
								scale : 1, // 地图缩放级别,整形值,范围从1~28。默认为最大
								infoUrl : '' // 在查看位置界面底部显示的超链接,可点击跳转
							});
				}
			});
}

myApp.addNotification({
			title : '来自好社惠的消息',
			subtitle : '',
			message : '好社惠商城即将上线，敬请期待。',
			media : '<img width="33" height="33" style="border-radius:100%" src="'
					+ imgUrl + '/image/portal/logo.jpg">'
		});

setTimeout("myApp.closeNotification('.notifications')", 3000);
