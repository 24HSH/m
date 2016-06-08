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

var view2 = myApp.addView('#view-2', {
			dynamicNavbar : true
		});
$$('#href-2').on('click', function() {
			if (view2.history.length == 1) {
				view2.router.load({
							url : appUrl + "/item/list.htm"
						});
			}
		});

var view3 = myApp.addView('#view-3', {
			dynamicNavbar : true
		});
$$('#href-3').on('click', function() {
			if (view3.history.length == 1) {
				view3.router.load({
							url : appUrl + "/facebook/index.htm"
						});
			}
		});

var view4 = myApp.addView('#view-4', {
			dynamicNavbar : true
		});
$$('#href-4').on('click', function() {
			if (view4.history.length == 1) {
				view4.router.load({
							url : appUrl + "/cart/index.htm",
							ignoreCache : true,
							reload : true
						});
			}
		});

var view5 = myApp.addView('#view-5', {
			dynamicNavbar : true
		});
$$('#href-5').on('click', function() {
			if (view5.history.length == 1) {
				view5.router.load({
							url : appUrl + "/member/index.htm"
						});
			}
		});

$$('#view_2_click').on('click', function() {
			if (view2.history.length == 1) {
				view2.router.load({
							url : appUrl + "/item/list.htm"
						});
			}

			$$('#href-2').addClass("active");
		});

function portal_homepage_cart_stats() {
	$$.get(appUrl + '/cart/stats.htm', {}, function(data) {
				if (data > 0) {
					$$('#portal/homepage/cart').addClass('badge bg-red');
					$$('#portal/homepage/cart').html(data);
				} else {
					$$('#portal/homepage/cart').removeClass('badge bg-red');
					$$('#portal/homepage/cart').html('');
				}
			});
}

portal_homepage_cart_stats();

myApp.addNotification({
			title : '来自好社惠的消息',
			subtitle : '',
			message : '好社惠商城即将上线，敬请期待。',
			media : '<img width="33" height="33" style="border-radius:100%" src="'
					+ imgUrl + '/image/portal/logo.jpg">'
		});

setTimeout("myApp.closeNotification('.notifications')", 3000);
