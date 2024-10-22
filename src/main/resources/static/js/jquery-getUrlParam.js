(function ($) {
	// 定义一个名为 getUrlParam 的函数，它接受一个参数名（name）
	$.getUrlParam = function (name) {
		// 创建一个正则表达式来匹配 URL 参数
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		// 从窗口位置的搜索部分获取查询字符串，并去掉第一个字符（?）
		var r = window.location.search.substr(1).match(reg);
		// 如果匹配成功，则返回解码后的参数值
		if (r != null)
			return unescape(r[2]);
		// 如果没有找到参数，则返回 null
		return null;
	}
})(jQuery);