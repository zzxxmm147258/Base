(function(win){
	$HOME = {
		showMenuID:[],
		homeAttr : {memuList:{}},
		loading:$('.iframe-loading').clone(),
		getFClass : function(clazz){
			if(!clazz) return '';
			clazz = clazz.replace(/^ /,'');
			var l = clazz.indexOf(' ');
			return l<0?clazz:clazz.substring(0,l);
		},
		pageSize : {//定义界面宽度尺寸便于重置
			body : {
				children : {
					head : {
						height : 0
					},
					footer : {
						height : 0
					},
					main : {
						children : {
							mNav : {
								top : 0,
								height : 60,
								children : {
									mNavLogo : {
										width : 136
									},
									mNavScroll : {},
									mNavTool : {
										width : 515,
										children : {
											mNavHide : {
												width : 20,
												lineHeight : true
											},
											mNavtTheme : {
												width : 40,
												lineHeight : true
											},
											mNavtUser : {
												width : 130,
												lineHeight : true
											},
											mNavtLogOut : {
												width : 40,
												lineHeight : true
											},
											mNavtHelp : {
												width : 40,
												lineHeight : true
											},
											mNavSearch:{
												width : 222,
												lineHeight : true,
												children : {
													mNavSearchDiv : {
														width : 200,
														height:26,
														lineHeight : true,
														children:{
															mNavSearchInput : {
																width : 150,
																height:'100%'
															},
															mNavSearchBtn:{
																width : 26,
																height:'100%'
															}
														}
													}
												}
											},
										}
									}
								}
							},
							mBody : {
								children : {
									mbMenu : {
										width : 200,
										left : 0,
										children : {
											mbmHide : {
												height : 40
											},
											mbmList : {}
										}
									},
									mbDiv : {
										children : {
											mbdNav : {
												height : 30,
												width : '100%'
											},
											mbdContent : {
												width : '100%'
											},
										}
									},
								}
							},
						}
					},
				}
			},
		},
		bMenuUtil : {
			thisAttr:{
				show:{
					time:400
				},
				
			},
			menu1ReHeight : ['mbMenu','mbmList','mbDiv','mbdContent'],
			setMenu : function(){
				var _this = this;
				$.Hajax({
					type:"post",
					url:$.Url("/main/menu"),
					dataType:"json",
					success:function(data){
						if(data.success){
							_this.setmNavMenu(data.datas);
						}
					}
				});
			},
			setmNavMenu : function(datas){
				if(datas){
					$HOME.homeAttr.mbdScroll = new IScroll('#mbdScroll',{fixedScrollbar:true,eventPassthrough: true,scrollX:true,scrollY:false,preventDefault:false,bounce:false,bindToWrapper:true});
					$HOME.homeAttr.mNavScroll = new IScroll('#mNavScroll',{fixedScrollbar:true,eventPassthrough: true,scrollX:true,scrollY:false,preventDefault:false,bounce:false,bindToWrapper:true});
					if(Envparam.homeUrl)$HOME.showPage('home', Envparam.homeUrl, '首页');
					for (var i = 0; i < datas.length; i++) {
						this.addmn1Menu(datas[i],i==0);
					}
				}
				$('#mbmHideImg,#mbMenuOpen').click(function(){//竖向菜单显隐
					$HOME.bMenuUtil.mwnu2Toggle("#mbMenu");
				});
				$('#mbMenuOpenImg').mouseover(function(){
					$HOME.setOperation(this,'on',true);
				}).mouseout(function(){
					$HOME.setOperation(this,'on',false);
				});
				$('#mNavHide').click(function(){
					$HOME.bMenuUtil.mwnu1Toggle('#mNav');
				});
			},
			addmn1Menu : function(mMenu,isSelect){//添加一级菜单
				var mNavScroll = $('#mNavScroller');
				var thisTab = $('<div class="mNavScrollTab" menuid="'+mMenu.menuid+'"></div>').appendTo(mNavScroll);
				thisTab.css('line-height',$('#mNavScroll').css('height'));
				var mNavsTabDiv = $('<div class="mNavsTabDiv"></div>').appendTo(thisTab);
				var mNavstText = $('<div class="mNavstText" menuid="'+mMenu.menuid+'" title="'+mMenu.menuname+'">'+mMenu.menuname+'</div>').appendTo(mNavsTabDiv).click(function(){
					$HOME.bMenuUtil.openMenu2(this,true);
				}).mouseover(function(){
					$HOME.setOperation(this,'on',true);
				}).mouseout(function(){
					$HOME.setOperation(this,'on',false);
				});
				if(isSelect)$HOME.setOperation(mNavstText,'select',true).addClass('mbmListPage-show');
				$($HOME.homeAttr.mNavScroll.scroller).width($HOME.getWidth($HOME.homeAttr.mNavScroll.scroller,200));
				this.addMb2Menu(mMenu,isSelect);
			},
			openMenu2 : function(target,isOpen){
				target = $(target);
				var menu2id = target.attr('menu2id');
				if(!target.hasClass('mbmListPage-show')){
					var oldMenuid = $HOME.setOperation('.mbmListPage-show','select',false).removeClass('mbmListPage-show').attr('menuid');
					$HOME.setOperation(target,'select',true).addClass('mbmListPage-show');
					var menu1id = target.attr('menuid');
					var wid = $HOME.findTarget('mbMenu', null, true).width;
					$('.mbmListPage[menuid="'+oldMenuid+'"]').animate({left:-wid}, $HOME.bMenuUtil.thisAttr.show.time);
					$('.mbmListPage[menuid="'+menu1id+'"]').css('left',wid).animate({left:0}, $HOME.bMenuUtil.thisAttr.show.time,function(){
						if(menu2id){
							$('.mbml2Menu[menuid="'+menu2id+'"]').click();
						}else{
							$($(this).find('.mbml2Menu')[0]).click();
						}
					});
				}else{
					setTimeout(function(){
						$('.mbml2Menu[menuid="'+menu2id+'"]').click();
					}, $HOME.bMenuUtil.thisAttr.show.time)
				}
				if($("#mbMenu").hasClass('mbMenu-hide')&&isOpen){
					$HOME.bMenuUtil.mwnu2Toggle("#mbMenu");
				}
			},
			addMb2Menu : function(mMenu,isShow){//添加二级菜单mbMenu
				var left = isShow?'0':$HOME.findTarget('mbMenu', null, true).width;
				var mbmListPage = $('<div class="mbmListPage" menuid="'+mMenu.menuid+'" style="left:'+left+'px;">').appendTo( "#mbmlScroll" );
				var menu2children = mMenu.children;
				for (var i = 0; i < menu2children.length; i++) {
					var menu2 = menu2children[i];
					var class2Menu = $('<div class="mbml2Menu" menu1id="'+mMenu.menuid+'" menuid="'+menu2.menuid+'" title="'+menu2.menuname+'">'+menu2.menuname+'</div>').appendTo(mbmListPage);
					class2Menu.click(function(){//绑定二级菜单点击时间
						if(!$(this).hasClass('mbml2MenuDiv-show')){
							$('.mbml2MenuDiv-show').removeClass('mbml2MenuDiv-show').next().slideUp(300);
							$(this).addClass('mbml2MenuDiv-show').next().slideDown(300);
							var menu1id = $(this).attr('menu1id');
							var menu2id = $(this).attr('menuid');
							$('.mNavstText[menuid="'+menu1id+'"]').attr('menu2id',menu2id);
						}
					}).mouseover(function(){
						$HOME.setOperation(this,'on',true);
					}).mouseout(function(){
						$HOME.setOperation(this,'on',false);
					});
					var div = $('<div class="mbml2MenuDiv"></div>').appendTo(mbmListPage);
					var children = menu2.children;
					for (var k = 0; k < children.length; k++) {
						var menu3 = children[k];
						var mnuDiv = $('<div class="mbmListTab" menuid="'+menu3.menuid+'" src="'+menu3.url+'" title="'+menu3.menuname+'">'+menu3.menuname+'<span class="mbmListTabImg"></span></div>').appendTo(div);
						mnuDiv.click(function(){//绑定三级菜单点击时间
							$HOME.bMenuUtil.openMenu3(this);
						}).mouseover(function(){
							$HOME.setOperation(this,'on',true);
						}).mouseout(function(){
							$HOME.setOperation(this,'on',false);
						});
						$HOME.homeAttr.memuList[menu3.menuid] = {menu1:mMenu.menuid,menu2:menu2.menuid,menu3:menu3.menuname};//缓存搜索数据
					}
					if(i==0&&isShow)class2Menu.click();	
				}
			},
			openMenu3:function(target){
				target = $(target);
				var menuid = target.attr('menuid');
				var src = target.attr('src');
				var name = target.text();
				$HOME.setOperation(target,'select',true);
				var page = $HOME.showPage(menuid,src,name,false);
				$HOME.homeAttr.mbdScroll.refresh();
				$HOME.homeAttr.mbdScroll.scrollToElement(page.tab[0]);
				$HOME.setOperation(target.children('.mbmListTabImg'),'select',true);
			},
			mwnu2Toggle : function(target,endfn){//二级导航菜单显示隐藏
				var mbMenu = $(target);
				if(mbMenu.hasClass('mbMenu-hide')){
					this.menu2Show(mbMenu,endfn);
				}else{
					this.menu2Hide(mbMenu,endfn);
				}
			},
			menu2Hide : function(target,endfn){//二级导航菜单隐藏
				var target = $(target);
				var width = target.outerWidth(true);
				target.addClass('mbMenu-hide').animate({left: -width}, this.thisAttr.show.time);
				var mbDiv = target.next().css({position:'absolute',left:width});
				mbDiv.animate({width: (mbDiv.width()+width),left:0}, this.thisAttr.show.time,function(){
					if(endfn){
						endfn({width:width},false);
					}
					var mbMenu = $HOME.findTarget($HOME.getFClass(target[0].className), null, true);
					mbMenu.left=-width;
					$('#mbMenuOpen').animate({left: width,'z-index': 400},$HOME.bMenuUtil.thisAttr.show.time);
				});
			},
			menu2Show : function(target,endfn){//二级导航菜单显示
				var target = $(target);
				var width = target.outerWidth(true);
				target.removeClass('mbMenu-hide').animate({left:0}, this.thisAttr.show.time);
				var mbDiv = target.next();
				mbDiv.animate({width: (mbDiv.width()-width),left:width}, this.thisAttr.show.time,function(){
					if(endfn){
						endfn({width:width},true);
					}
					var mbMenu = $HOME.findTarget($HOME.getFClass(target[0].className), null, true);
					mbMenu.left=0;
					$('#mbMenuOpen').css('z-index',0).animate({left: -width},$HOME.bMenuUtil.thisAttr.show.time);
				});
			},
			mwnu1Toggle : function(target,endfn){//一级导航菜单显示隐藏
				var mNav = $(target);
				if(mNav.hasClass('mNav-hide')){
					this.menu1Show(mNav,endfn);
				}else{
					this.menu1Hide(mNav,endfn);
				}
			},
			menu1Hide : function(target,endfn){//一级导航菜单隐藏
				var target = $(target);
				var height = target.outerHeight(true);
				target.addClass('mNav-hide').animate({top:-height}, this.thisAttr.show.time);
				var mNavDiv = target.next().css({position:'absolute',top:height});
				mNavDiv.animate({height: (mNavDiv.height()+height),top:0}, this.thisAttr.show.time,function(){
					if(endfn){
						endfn({height:height},true);
					}
					var mNavMenu = $HOME.findTarget($HOME.getFClass(target[0].className), null, true);mNavHideImg
					mNavMenu.top=-height;
					$('#mNavHideImg').animate({bottom: '-20px'},$HOME.bMenuUtil.thisAttr.show.time,function(){
						$HOME.setOperation(this,'down',true);
					});
					
				});
				for ( var i in this.menu1ReHeight) {
					var div = $('#'+this.menu1ReHeight[i]);
					div.animate({height: (div.height()+height)}, this.thisAttr.show.time);
				}
			},
			menu1Show : function(target,endfn){//一级导航菜单显示
				var target = $(target);
				var height = target.outerHeight(true);
				target.removeClass('mNav-hide').animate({top:0}, this.thisAttr.show.time);
				var mNavDiv = target.next();
				mNavDiv.animate({height: (mNavDiv.height()-height),top:height}, this.thisAttr.show.time,function(){
					if(endfn){
						endfn({height:height},true);
					}
					var mNavMenu = $HOME.findTarget($HOME.getFClass(target[0].className), null, true);
					mNavMenu.top=0;
					$('#mNavHideImg').animate({bottom: '37%'},$HOME.bMenuUtil.thisAttr.show.time,function(){
						$HOME.setOperation(this,'down',false);
					});
				});
				for ( var i in this.menu1ReHeight) {
					var div = $('#'+this.menu1ReHeight[i]);
					div.animate({height: (div.height()-height)}, this.thisAttr.show.time);
				}
			}
			
		},
		searchMenu3 : function(value,obj){//搜索界面菜单
			obj = $(obj).empty();
			var menuList = $HOME.homeAttr.memuList;
			var i = 0;
			for ( var id in menuList) {
				var isName = true;
				for (var k = 0; k < value.length; k++) {
					isName = isName&&(new RegExp(value.charAt(k),"gi").test(menuList[id].menu3));
				}
				var isId = new RegExp(value,"gi").test(id);
				isId = value?isId:true;
				if(isName||isId){
					var menuobj = menuList[id];
					var index = ++i<100?('0'+i):i;
					index = i<10?('0'+index):index;
					$('<div class="mNavSearchTab" menu3id="'+id+'" menu2id="'+menuobj.menu2+'" menu1id="'+menuobj.menu1+'" title="'+menuobj.menu3+'">'+index+'-'+menuobj.menu3+'</span></div>').appendTo(obj).click(function(){
						var _this = $(this);
						var menu1id = _this.attr('menu1id');
						var menu2id = _this.attr('menu2id');
						var menu3id = _this.attr('menu3id');
						$HOME.bMenuUtil.openMenu2($('.mNavstText[menuid="'+menu1id+'"]').attr('menu2id',menu2id),false);
						$HOME.bMenuUtil.openMenu3('.mbmListTab[menuid="'+menu3id+'"]');
						$('#mNavSearchBox').slideUp(400,function(){
							obj.empty();
						});
					}).mouseover(function(){
						$(this).addClass('mNavSearchTab-mouseover');
						$HOME.setOperation(this,'on',true);
					}).mouseout(function(){
						$(this).removeClass('mNavSearchTab-mouseover');
						$HOME.setOperation(this,'on',false);
					});
				}
			}
		},
		showPage : function(menuid,src,name,isRefresh,pmenuid){//打开或显示菜单内容
			var parent = $('#mbdContent');
			var perv = $HOME.showMenuID.length-1;
			if(perv>-1){
				$('.iframeDiv[menuid="'+$HOME.showMenuID[perv]+'"]').hide();
				$HOME.setOperation('.mbdScrollTab[menuid="'+$HOME.showMenuID[perv]+'"]','select',false);
				var ptab = $HOME.setOperation('.mbdstText[menuid="'+$HOME.showMenuID[perv]+'"]','select',false);
				$HOME.setOperation(ptab.parent().find('.mbdstTextIcon'),'select',false);
			}
			var mAt = $.inArray(menuid,$HOME.showMenuID);
			var thisIframe = $('.iframeDiv[menuid="'+menuid+'"]');
			var thisTab = $('.mbdScrollTab[menuid="'+menuid+'"]');
			if(mAt>-1){
				thisIframe.show();
				if(isRefresh){
					thisIframe.children('.loading-'+menuid).show();
					try{
						thisIframe.children('.iframe').css({visibility: 'hidden'})[0].contentWindow.location.reload();
					}catch(e){
						thisIframe.children('.iframe').css({visibility: 'hidden'}).attr('src',thisIframe.children().attr('src'));
					}
				};
				this.showMenuID.splice(mAt,1);
			}else{
				var mbdScroll = $('#mbdScroller');
				thisTab = $('<div class="mbdScrollTab" menuid="'+menuid+'"></div>').appendTo(mbdScroll);
				thisTab[0].oncontextmenu=function(e){
					$HOME.setOncontextMenu.show($(this).attr('menuid'),e);
				};
				var mbdsTabDiv = $('<div class="mbdsTabDiv"></div>').appendTo(thisTab);
				$('<div class="mbdstText" menuid="'+menuid+'" title="双击试试">'+name+'</div>').appendTo(mbdsTabDiv).click(function(){
					$HOME.showPage($(this).attr('menuid'), null, null,false);
				}).dblclick(function(){
					var mbMenu = $('#mbMenu');
					var mNav = $('#mNav');
					if(mNav.hasClass('mNav-hide')&&mbMenu.hasClass('mbMenu-hide')){
						$HOME.bMenuUtil.menu2Show(mbMenu);
						$HOME.bMenuUtil.menu1Show(mNav);
					}else{
						if(!mbMenu.hasClass('mbMenu-hide'))$HOME.bMenuUtil.menu2Hide(mbMenu);
						if(!mNav.hasClass('mNav-hide'))$HOME.bMenuUtil.menu1Hide(mNav);
					}
				}).mouseover(function(){
					$HOME.setOperation(this,'on',true);
				}).mouseout(function(){
					$HOME.setOperation(this,'on',false);
				});
				var optioner = $('<div class="mbdstText-optioner"></div>').appendTo(mbdsTabDiv);
				if('home'!=menuid){
					$('<div class="mbdstTextIcon mbdstTextIcon-close" menuid="'+menuid+'"></div>').appendTo(optioner).click(function(){
						$HOME.delPage($(this).attr('menuid'));
					});
				}else{
					$('<div class="mbdstTextIcon mbdstTextIcon-refresh" menuid="'+menuid+'"></div>').appendTo(optioner).click(function(){
						$HOME.showPage($(this).attr('menuid'), null, null,true);
					}); 
				}
				thisIframe = $('<div class="iframeDiv" menuid="'+menuid+'" pmenuid="'+(pmenuid?pmenuid:'')+'"><iframe class="iframe" src="'+$.Url(src)+'">sss</iframe></div>').appendTo('#mbdContent');
				var loadClone = this.loading.clone();
				loadClone.addClass('loading-'+menuid).appendTo(thisIframe).show();
				thisIframe.children('.iframe').css({visibility: 'hidden'}).load(function() {
					 $(this).css({visibility: 'visible'}).next().hide();
				});
			}
			$HOME.setOperation(thisTab,'select',true);
			$HOME.setOperation(thisTab.find('.mbdstText'),'select',true);
			if('home'!=menuid)$HOME.setOperation(thisTab.find('.mbdstTextIcon'),'select',true);
			this.showMenuID.push(menuid);
			if(this.showMenuID.length<2)thisTab.css('margin-left','15px');
			$(this.homeAttr.mbdScroll.scroller).width(this.getWidth(this.homeAttr.mbdScroll.scroller,400)+200);
			return {tab:thisTab,iframe:thisIframe};
		},		
		delPage:function(menuid){//关闭打开的标签
			if(menuid!='home'){
				var mAt = $.inArray(menuid,this.showMenuID);
				var last = this.showMenuID.length-1;
				if(mAt==last&&last>0){//是否 关闭的是最后一个
					var showMenuid = this.showMenuID[last-1];
					$('.iframeDiv[menuid="'+showMenuid+'"]').show();
					this.homeAttr.mbdScroll.scrollToElement($HOME.setOperation('.mbdScrollTab[menuid="'+showMenuid+'"]','select',true)[0]);
					var ptab = $HOME.setOperation('.mbdstText[menuid="'+showMenuid+'"]','select',true);
					if('home'!=showMenuid)$HOME.setOperation(ptab.parent().find('.mbdstTextIcon'),'select',true);
				}
				this.showMenuID.splice(mAt,1);
				$('.iframeDiv[menuid="'+menuid+'"]').remove();
				$HOME.setOperation($('.mbmListTab[menuid="'+menuid+'"]'),'select',false);
				$HOME.setOperation($('.mbmListTab[menuid="'+menuid+'"]>.mbmListTabImg'),'select',false);
				var thisTab = $('.mbdScrollTab[menuid="'+menuid+'"]').remove();
				$($('.mbdScrollTab')[0]).css('margin-left','15px');
				$(this.homeAttr.mbdScroll.scroller).width(this.getWidth(this.homeAttr.mbdScroll.scroller,400)+200);
				this.homeAttr.mbdScroll.refresh();
			}
		},
		setOncontextMenu : {//设置右键菜单
			isBlur:false,
			mbdScrollTab : null,
			show : function(menuid,event){
				$('#oncontextmenu-menu>li').hide();
				var last = $HOME.showMenuID.length-1;
				this.mbdScrollTab = $('.mbdstText');
				var isHome = $(this.mbdScrollTab[0]).attr('menuid')=='home';
				last = isHome?last:(last+1);
				if(last>0){
					var showArray = [];
					this.mbdScrollTab.each(function(mAt){
						var smenu = $(this).attr('menuid');
						mAt = isHome?mAt:(mAt+1);
						if(smenu==menuid){
							if(mAt==0){
								showArray = [4]
							}else if(mAt==last&&mAt==1){
								showArray = [0];
							}else if(mAt==last){
								showArray = [0,3,5];
							}else if(mAt==1){
								showArray = [0,3,4];
							}else{
								showArray = [0,2,3,4,5];
							}
							return false;
						}
					});
					for ( var i in showArray) {
						$('#oncontextmenu-menu-'+showArray[i]).show();
					}
					$("#oncontextmenu").show().offset({top:event.pageY-15,left:event.pageX});
					$('#oncontextmenuFocus').val(menuid).focus();
				}
			},
			event : function(e){
				var _this = this;
				$('#oncontextmenu-menu>li').mouseover(function(){
					var menuid = $('#oncontextmenuFocus').val();
					var clv = $(this).attr('val');
					var isChange = false;
					$HOME.setOncontextMenu.mbdScrollTab.each(function(mAt){
						var mid = $(this).attr('menuid');
						if(menuid==mid)isChange = true;
						if(clv==0||clv==1){
							if(isChange){
								$(this).addClass('mbdstText-on');
								return false;
							};
						}else if(clv==2&&mid!='home'){
							if(!isChange)$(this).addClass('mbdstText-on');
							isChange = false;
						}else if(clv==5&&mid!='home'){
							if(!isChange){$(this).addClass('mbdstText-on')}else{
								return false;
							};
						}else if(clv==3&&mid!='home'){
							$(this).addClass('mbdstText-on');
						}else{
							if(isChange&&menuid!=mid)$(this).addClass('mbdstText-on');
						}
					});
					$(this).addClass('oncontextmenu-menu-li-on');
				}).mouseout(function(){
					$HOME.setOncontextMenu.mbdScrollTab.removeClass('mbdstText-on');
					$(this).removeClass('oncontextmenu-menu-li-on');
				}).click(function(){
					var menuid = $('#oncontextmenuFocus').val();
					var clv = $(this).attr('val');
					if(clv==0){//刷新
						$HOME.showPage(menuid, null, null,true);
					}else if(clv==1){//关闭当前
						$HOME.delPage(menuid);
					}else if(clv==2){//关闭其它
						var newArray = $HOME.showMenuID.slice();
						for ( var i in newArray) {
							var thisMenuid = newArray[i];
							if(thisMenuid!=menuid){
								$HOME.delPage(thisMenuid);
							}
						}
					}else if(clv==3){//关闭所有
						var newArray = $HOME.showMenuID.slice();
						for ( var i in newArray) {
							$HOME.delPage(newArray[i]);
						}
					}else if(clv==4){//关闭右侧
						var mbdScroller = $('#mbdScroller').children();
						for (var i=mbdScroller.length-1; i>=0;i--){
							var thisMenuid = $(mbdScroller[i]).attr('menuid');
							if(thisMenuid==menuid){
								break;
							}
							$HOME.delPage(thisMenuid);
						}
					}else if(clv==5){//关闭左侧
						var mbdScroller = $('#mbdScroller').children();
						var isDel= false;
						for (var i=mbdScroller.length-1; i>=0;i--){
							var thisMenuid = $(mbdScroller[i]).attr('menuid');
							if(thisMenuid==menuid){
								isDel = true;
								continue;
							}
							if(isDel){
								$HOME.delPage(thisMenuid);
							}
						}
					}
					$("#oncontextmenu").hide();
				});
				$('#oncontextmenuFocus').blur(function(){
					if($('.oncontextmenu-menu-li-on').length<=0){
						$("#oncontextmenu").hide();
						$HOME.setOncontextMenu.mbdScrollTab.removeClass('mbdstText-on');
						$HOME.setOncontextMenu.mbdScrollTab=null;
					}
				});
			}
		},
		getWidth : function(o,dv){
			var ob = $(o).children();
			var w = 0;
			ob.each(function(){
				w = w + $(this).outerWidth(true);
			});
			w = w>dv?w:dv;
			return w;
		},
		setTheme : function(theme){
			if(!theme){
				theme = window.localStorage.getItem('Hibo-Home-Theme');
			}else{
				window.localStorage.setItem('Hibo-Home-Theme',theme);
			}
			if(theme){
				$('<link id="home-theme" rel="stylesheet" type="text/css" href="'+ $.Url('/resources/bas/css/home/skin/'+theme+'-theme.css') + '"/>').appendTo('head');
			}else{
				$('<link id="home-theme" rel="stylesheet" type="text/css"/>').appendTo('head');
			}
			$('.mNavttBoxBtn[theme="'+theme+'"]').hide();
		},
		changeTheme : function(theme){
			var oldTheme = window.localStorage.getItem('Hibo-Home-Theme');
			var time = 200;
			var dealTheme = function(dealtheme,dealOldTheme){
				$('.mNavttBoxBtn[theme="'+dealtheme+'"]').hide();
				$('.mNavttBoxBtn[theme="'+dealOldTheme+'"]').show();
				window.localStorage.setItem('Hibo-Home-Theme',dealtheme);
			}
			$('#hBody').fadeOut(time,function(){
				if(theme){
					var url = $.Url('/resources/bas/css/home/skin/'+theme+'-theme.css');
					$('#home-theme').attr('href',url);
					$.ajax({type:"get",url:url,timeout:2000,
						success:function(data){
							$('#hBody').fadeIn(time);
							dealTheme(theme,oldTheme);
							$HOME.resize();
						},
						error:function(){
							dealTheme(oldTheme,oldTheme);
							$HOME.setTheme(oldTheme);
							$('#hBody').fadeIn(time,function(){
								$Message.alert('这个主题好像失踪了(T_T)');
							});
						}
					});
				}else{
					$('#home-theme').removeAttr('href');
					$HOME.resize();
					$('#hBody').fadeIn(time);
					dealTheme(theme,oldTheme);
				}
			});
		},
		setAttrs : function(target,targetAttr){
			var target = $(target);
			for ( var attr in targetAttr) {
				var val = targetAttr[attr];
				attr = attr.replace(/[A-Z]/g, function(att){
					return '-' + att.toLowerCase();
				});
				target.css(attr,val);
			}
			return target;
		},
		setOperation : function(target,attr,isAdd){
			var target = $(target);
			if(target.length<=0){
				return target
			}
			var tab = this.getFClass(target[0].className)
			if(isAdd){
				target.addClass(tab+'-'+attr);
			}else{
				target.removeClass(tab+'-'+attr);
			}
			return target;
		},
		findTarget : function(className,o,isFirst){
			var ro = false;
			if(o||isFirst){
				if(isFirst)o=this.pageSize;
				for ( var clazz in o) {
					var tar = o[clazz];
					if(className==clazz){
						ro = tar;
						break;
					}else{
						ro = this.findTarget(className, tar.children, false);
						if(ro)break;
					}
				}
			}
			return ro;
		},
		calSize: function(pageSize,bodyWidth,bodyHeight){
			var wcount = 0;
			var hcount = 0;
			for ( var name in pageSize) {
				var o = pageSize[name];
				var w = o.width,h = o.height;
				var tar = $('#'+name);
				if($.type(w)!= "string"){
					if($.type(w) == "number"){
						wcount = wcount + o.width;
					}else{
						w = bodyWidth-wcount;
					}
					wcount = wcount + tar.outerWidth(true) - tar.width();
					tar.width(w);
				}else{
					tar.css('width',w);
				}
				if($.type(h)!= "string"){
					if($.type(h) == "number"){
						hcount = hcount + o.height;
					}else{
						h = bodyHeight-hcount;
					}
					hcount = hcount + tar.outerHeight(true) - tar.height();
					var pare = tar.parent();
					//var kuang = pare.outerHeight(true)-pare.innerHeight();
					tar.height(h);
				}else{
					tar.css('height',h);
				}
				if(o.top){
					hcount = hcount + o.top;
					tar.offset({top:o.top});
				}
				if(o.left){
					wcount = wcount + o.left;
					tar.offset({left:o.left});
				}
				if($.type(o.lineHeight)== "boolean"){
					tar.css('line-height',h+'px');
				}else if($.type(o.lineHeight)== "number"){
					tar.css('line-height',o.lineHeight+'px');
				}
				if(o.children){
					this.calSize(o.children, tar.innerWidth(), tar.innerHeight());
				}
			}
		},
		resize : function(){
			var bodyWidth = document.documentElement.clientWidth;
			var bodyHeight = document.documentElement.clientHeight-2;
			this.calSize(this.pageSize, bodyWidth, bodyHeight)
			this.loading.find('.iframe-loading-div').css('font-size',bodyWidth/25);
			$('.iframe-loading-div').css('font-size',bodyWidth/25);
		},
		initInfo : function(){
			$('title').text(Envparam.title);
			//绑定事件
			$('div').mouseover(function(event){
				$HOME.setOperation(this,'on',true);
			}).mouseout(function(){
				$HOME.setOperation(this,'on',false);
			});
			
			$('#mNavLogoImg').click(function(){
				window.location.reload(true);
			});
			
			var username = Envparam.user.truename?Envparam.user.truename:Envparam.user.username;
			var sex = -1;
			if(Envparam.user.sex||$.type(Envparam.user.sex)=='boolean'){
				sex = Envparam.user.sex=='true'||Envparam.user.sex==true?1:0;
			}
			var headpic = Envparam.user.headpicture;
			if(sex==0){
				$('#mNavtUserImg').addClass('mNavtUser-woman');
				headpic = headpic?headpic:$.Url('/resources/image/women.png');
			}else if(sex==1){
				$('#mNavtUserImg').addClass('mNavtUser-man');
				headpic = headpic?headpic:$.Url('/resources/image/men.png');
			}else{
				headpic = headpic?headpic:$.Url('/resources/image/defaultImg.png');
			}
			
			$('#userInfoHead>img').attr('src',headpic);
			if(Envparam.user.nickname){
				$('#userInfoNike').text(Envparam.user.nickname).attr('title',Envparam.user.nickname);
			}else{
				$('#userInfoNike').text('暂无昵称').attr('title','暂无昵称').css('color','#828282');
			}
			
			$('#mNavtUserInfo').click(function(event){
				$('#userInfo').slideDown(400).offset({top:($('#mNav').height())});
			}).mouseout(function(){
				$('#userInfo').slideUp(400);
			}).text(username).attr('title',Envparam.user.username);
			$('#mNavttBtn').click(function(e){//切换主题
				if($(this).hasClass('mNavttBtn-select')){
					$HOME.setOperation(this,'select',false);
					$('#mNavttBox').slideUp(400);
				}else{
					$HOME.setOperation(this,'select',true);
					$('#mNavttBox').slideDown(400).offset({top:($('#mNav').height())});
					$('#mNavttInput').focus();
				}
			}).mouseover(function(event){
				$('#mNavttInput').addClass('focus');
			}).mouseout(function(){
				$('#mNavttInput').removeClass('focus');
			});
			$('.mNavttBoxBtn').mouseover(function(event){
				$('#mNavttInput').addClass('focus');
			}).mouseout(function(){
				$('#mNavttInput').removeClass('focus');
			});
			
			$('#mNavttInput').blur(function(event){
				if(!$(this).hasClass('focus')){
					$('#mNavttBtn').click();
				}
			});
			
			$('.mNavttBoxBtn').click(function(){
				$HOME.changeTheme($(this).attr('theme'));
				$('.mNavttBtn').click();
			});
			
			$('#mNavtLogOut').click(function(){
				$Message.confirm('确认退出?', function(){location.href=$.Url('/logout');});
			});
			
			$('#mNavtHelp').click(function(){
				$HOME.showPage("hiboHelp", $.Url('/main/cms/help.html'), '帮助', false);
			});
			
			var keyCodeNum=-1;
			var attrdiv = null;
			$('#mNavSearchBtn').click(function(){
				$HOME.searchMenu3($('#mNavSearchInput').focus().val(), '#mNavSearchList');
				$('#mNavSearchBox').slideDown(400).scrollTop(0);
				attrdiv = null;
			}).mouseover(function(){
				$(this).addClass('mNavSearchTab-mouseover');
			}).mouseout(function(){
				$(this).removeClass('mNavSearchTab-mouseover');
			});
			
			$('#mNavSearchInput').focus(function(){
				keyCodeNum = -1;
				$HOME.setOperation('#mNavSearchBtn','focus',true);
			}).blur(function(){
				$HOME.setOperation('#mNavSearchBtn','focus',false);
				if($('.mNavSearchTab-mouseover').length<=0){
					$('#mNavSearchBox').slideUp(400);
				}
				attrdiv = null;
			}).keydown(function(event){
				var btn = $('#mNavSearchBtn');
				var attrdivs = $('#mNavSearchList').children();
				switch(event.keyCode) {
					case 38:
						if(keyCodeNum>=0)$HOME.setOperation(attrdivs[keyCodeNum],'keyDown',false);
						keyCodeNum--;
						keyCodeNum=keyCodeNum<0?(attrdivs.length-1):keyCodeNum
						attrdiv = attrdivs[keyCodeNum];
						var maxScroll = attrdiv.parentNode.offsetHeight-attrdiv.offsetHeight-1;
						var minScroll = attrdiv.offsetParent.scrollTop-1;
						if(attrdiv.offsetTop<minScroll||attrdiv.offsetTop>maxScroll){
							attrdiv.offsetParent.scrollTop=attrdiv.offsetTop;
						}
						if(attrdiv.offsetTop-attrdiv.offsetParent.scrollTop>attrdiv.offsetParent.offsetHeight){
							var min = attrdiv.offsetParent.offsetHeight-attrdiv.offsetHeight;
							attrdiv.offsetParent.scrollTop=attrdiv.offsetTop-min;
						}
						$HOME.setOperation(attrdiv,'keyDown',true);
						return false;
						break;
					case 40:
						if(keyCodeNum>=0)$HOME.setOperation(attrdivs[keyCodeNum],'keyDown',false);
						keyCodeNum++;
						keyCodeNum=keyCodeNum>=attrdivs.length?0:keyCodeNum;
						attrdiv = attrdivs[keyCodeNum];
						var maxScroll = attrdiv.parentNode.offsetHeight-1;
						var minScroll = attrdiv.offsetParent.offsetHeight-attrdiv.offsetHeight;
						var heiTop = attrdiv.offsetTop-attrdiv.offsetParent.scrollTop;
						if(heiTop>minScroll){
							attrdiv.offsetParent.scrollTop=attrdiv.offsetTop-minScroll;
						}
						if(attrdiv.offsetTop==0||heiTop<0){
							attrdiv.offsetParent.scrollTop=attrdiv.offsetTop;
						}
						$HOME.setOperation(attrdiv,'keyDown',true);
						return false;
						break;
					case 13:
						if(attrdiv){
							attrdiv.click();
							$(this).focus();
						}else{
							btn.click();
						}
						attrdiv = null;
						break;
					default : 
						attrdiv = null;
						$('#mNavSearchBox').slideUp(400);
						break;
				}
			});
		},
		init : function(){
			document.oncontextmenu=function(e){return false}//禁用右键
			$('.iframe-loading').remove();
			this.bMenuUtil.setMenu();//初始化菜单
			this.setTheme();//设置主题;
			this.resize();//初始化框架
			this.initInfo();
			$(window).resize(function(){$HOME.resize()});
			this.setOncontextMenu.event()//绑定右键事件
			$('#hBody').fadeIn(1000,function(){
				$HOME.resize();
			});
		},
	};
	$HOME.init();
})(window)