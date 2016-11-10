// JavaScript Document

$(document).ready(function(){
	
	// tab
	$('.def-tab').show();
	$('ul.tab-match li a').click(function(){
		if(!$(this).hasClass('activeTab')){
			$('ul.tab-match li a').removeClass('activeTab');
			$(this).addClass('activeTab');
			$('.tab-block').slideUp();
			var activeTab = $(this).attr('href');
			$(activeTab).slideDown();
		}
		return false
	});
	
	// tab
	$('.def-tab-bbw').show();
	$('ul.tab-bbw li a').click(function(){
		if(!$(this).hasClass('activeTab')){
			$('ul.tab-bbw li a').removeClass('activeTab');
			$(this).addClass('activeTab');
			$('.tab-bbw-blk').slideUp();
			var activeTab = $(this).attr('href');
			$(activeTab).slideDown();
		}
		return false
	});
	
	// Trigger
	$('a.login-trigger').click(function(){
		$('ul.acc-li').stop(true, true).slideToggle();
	});
	
	// Drop Down
	$('.tooltip').wrapInner('<span />');
	$('ul.topNav li a').hover(function(){
		var pareNt = $(this).parent();
		pareNt.find('ul.subNav').stop(true, true).slideDown('slow');
		pareNt.find('.tooltip').stop(true, true).fadeIn();
		$(this).addClass('hovered');
		pareNt.hover(function(){
		}, function(){
			$(this).find('a').removeClass('hovered');
			pareNt.find('ul.subNav').slideUp('slow');
			pareNt.find('.tooltip').fadeOut();
		});
	});
	$('.tooltip').hover( function(){$('.tooltip').hide();});
	$('ul.subNav li').hover( function(){$('.tooltip').delay(700).fadeOut();});
	
	// Tooltip
	$('#edit-profile ul li span a').hover(function(){
		$(this).addClass('tooltip-select');
		$(this).parent().find('.tooltip-box').stop(true, true).fadeIn('slow');
		$(this).parent().hover(function(){
		}, function(){
			$(this).removeClass('tooltip-select');
			$(this).parent().find('.tooltip-box').fadeOut('slow');
		});
	});
});