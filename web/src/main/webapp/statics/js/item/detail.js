myApp.onPageInit('item.detail', function(page) {
			$$('form.ajax-submit').on('beforeSubmit', function(e) {
					});

			$$('form.ajax-submit').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									// 更新首页购物车标记
									portal_homepage_cart_stats();
								});
					});

			$$('form.ajax-submit').on('submitError', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('.open-picker').on('click', function() {
				$('.page-content .item-detail-overlay')
						.addClass('item-detail-overlay-visible');
			});
			$$('.close-picker').on('click', function() {
				$('.page-content .item-detail-overlay')
						.removeClass('item-detail-overlay-visible');
			});
		});

function item_detail_cart(itemId, skuId) {
	myApp.showIndicator();

	$$('#item_detail_cart_itemId').val(itemId);
	$$('#item_detail_cart_skuId').val(skuId);
	$$('#item/detail/cart').trigger("submit");
}