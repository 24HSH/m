myApp.onPageInit('deliver.time', function(page) {
			$$('form.ajax-submit.deliver-time-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.deliver-time-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									myApp.getCurrentView().router.back();
								});
					});

			$$('form.ajax-submit.deliver-time-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('.content-block-inner .date').find('p').each(function() {
				$$(this).click(function() {
					if ($$(this).hasClass("cur")) {
						return;
					}

					var date = $$('.date  .sendDate.cur');
					// 1. 日期
					date.removeClass("cur");
					// 2. 时间
					var tab = date.data("tab");
					$$('.content-block-inner .time').find('div').each(
							function() {
								if (tab == $$(this).data("tab")) {
									$$(this).hide();
								}
							});
					// 3. 时间
					$$('.time .sendTime.cur').removeClass("cur");

					// 1. 日期
					$$(this).addClass("cur");
					// 2. 时间
					var tab = $$(this).data("tab");
					$$('.content-block-inner .time').find('div').each(
							function() {
								if (tab == $$(this).data("tab")) {
									$$(this).show();
								}
							});
				});
			});

			$$('.time .time_div').find('p').each(function() {
						$$(this).click(function() {
									if ($$(this).hasClass("cur")) {
										return;
									}

									$$('.time .sendTime.cur')
											.removeClass("cur");

									$$(this).addClass("cur");
								});
					});
		});

function deliver_time_set() {
	$$('#deliver_time_date').val($$('.date .sendDate.cur').data("tab"));

	var time = $$('.time .sendTime.cur');
	$$('#deliver_time_startTime').val(time.data("starttime"));
	$$('#deliver_time_endTime').val(time.data("endtime"));

	myApp.showIndicator();

	$$('#deliver/time/set').trigger("submit");
}
