myApp.onPageInit('deliver.time', function(page) {

			$$('.content-block-inner .date').find('p').each(function() {
				$$(this).click(function() {
					if ($$(this).hasClass("cur")) {
						return;
					}

					$$('.content-block-inner .date').find('p').each(function() {
						if ($$(this).hasClass("cur")) {
							// 1. 日期
							$$(this).removeClass("cur");
							// 2. 时间
							var tab = $$(this).data("tab");
							$$('.content-block-inner .time').find('div').each(
									function() {
										if (tab == $$(this).data("tab")) {
											$$(this).hide();
										}
									});
						}
					});

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

									$$('.time .time_div').find('p').each(
											function() {
												if ($$(this).hasClass("cur")) {
													$$(this).removeClass("cur");
												}
											});

									$$(this).addClass("cur");
								});
					});
		});
