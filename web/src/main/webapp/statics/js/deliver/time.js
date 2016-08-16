myApp.onPageInit('deliver.time', function(page) {

			$$('.content-block-inner .date').find('p').each(function() {
				$$(this).click(function() {
					if ($$(this).hasClass("cur")) {
						return;
					}

					$$('.content-block-inner .date').find('p').each(function() {
						if ($$(this).hasClass("cur")) {

							$$(this).removeClass("cur");

							var tab = $$(this).data("tab");
							$$('.content-block-inner .time').find('div').each(
									function() {
										if (tab == $$(this).data("tab")) {
											$$(this).hide();
										}
									});
						}
					});

					$$(this).addClass("cur");
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
