myApp.onPageInit('item.list', function(page) {

			$$('.close-picker').on('click', function() {
				$('.page-content .item-list-overlay')
						.removeClass('item-list-overlay-visible');
			});
		});

function item_list_scan() {
	wx.scanQRCode({
		needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		scanType : ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
		success : function(res) {
			var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果

			var mySearchbar = $$('.searchbar.item-list-searchbar')[0].f7Searchbar;
			mySearchbar.search(result.split(',')[1]);
		}
	});
}

function item_list_picker() {
	$('.page-content .item-list-overlay').addClass('item-list-overlay-visible');
	myApp.pickerModal('.picker-item-list');
}
