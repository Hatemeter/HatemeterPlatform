(window.webpackJsonp=window.webpackJsonp||[]).push([[5],{180:function(e,a,t){},184:function(e,a,t){e.exports=t(318)},272:function(e,a,t){},318:function(e,a,t){"use strict";t.r(a);t(185),t(213),t(214),t(242),t(246),t(248),t(259);!function(){if("function"===typeof window.CustomEvent)return!1;function e(e,a){a=a||{bubbles:!1,cancelable:!1,detail:void 0};var t=document.createEvent("CustomEvent");return t.initCustomEvent(e,a.bubbles,a.cancelable,a.detail),t}e.prototype=window.Event.prototype,window.CustomEvent=e}();var n=t(1),l=t.n(n),r=t(24),s=t.n(r),c=(t(180),t(12)),m=t(13),o=t(15),i=t(14),u=t(16),d=t(1057),p=t(1060),E=t(1050),g=(t(272),t(59)),v=t.n(g),h=t(1058),f=t(3),N=t(165),b=t(8),x={items:[{title:!0,name:"Data Analysis",wrapper:{element:"",attributes:{}},class:""},{name:"Recent trends",url:"/generalTrends",icon:"fa fa-line-chart"},{name:"Hashtag trends",url:"/hashtagTrends",icon:"fa fa-hashtag"},{name:"Hate speakers",url:"/hateSpeakers",icon:"fa fa-address-book"},{title:!0,name:"COMPUTER ASSISTED PERSUASION",wrapper:{element:"",attributes:{}},class:""},{name:"Counter-narratives",icon:"icon-pencil",url:"/counterNarratives"},{name:"Alerts",icon:"icon-flag",url:"/alerts"},{title:!0,name:"PROJECT HATEMETER",wrapper:{element:"",attributes:{}},class:""},{name:"Hatemeter Platform",icon:"icon-info",url:"/generalInfo"}]},w=t(42),k=t.n(w);function O(){return l.a.createElement("div",null,"Loading...")}var y=k()({loader:function(){return Promise.all([t.e(1),t.e(2),t.e(3),t.e(13),t.e(6)]).then(t.bind(null,1052))},loading:O}),j=k()({loader:function(){return Promise.all([t.e(0),t.e(7)]).then(t.bind(null,1053))},loading:O}),S=k()({loader:function(){return Promise.all([t.e(0),t.e(1),t.e(2),t.e(4),t.e(8)]).then(t.bind(null,1054))},loading:O}),P=k()({loader:function(){return Promise.all([t.e(0),t.e(1),t.e(2),t.e(3),t.e(9)]).then(t.bind(null,1055))},loading:O}),C=k()({loader:function(){return Promise.all([t.e(4),t.e(10)]).then(t.bind(null,1059))},loading:O}),T=k()({loader:function(){return Promise.all([t.e(0),t.e(1),t.e(11)]).then(t.bind(null,1056))},loading:O}),H=[{path:"/",name:"Home",component:G,exact:!0},{path:"/generalTrends",name:"Recent trends",component:y},{path:"/generalInfo",name:"HateMeter Platform",component:j},{path:"/hashtagTrends",name:"Hashtag Trends",component:S},{path:"/hateSpeakers",name:"Hate Speakers",component:P},{path:"/counterNarratives",name:"Counter-narratives",component:C},{path:"/alerts",name:"Alerts",component:T}],A=t(49),J=function(e){function a(){return Object(c.a)(this,a),Object(o.a)(this,Object(i.a)(a).apply(this,arguments))}return Object(u.a)(a,e),Object(m.a)(a,[{key:"render",value:function(){var e=this.props;e.children,Object(A.a)(e,["children"]);return l.a.createElement(l.a.Fragment,{style:{backgroundColor:"#fff"}},l.a.createElement("img",{className:"img-fluid",src:"assets/img/EU-Loghi_Hatemeter.png",width:"",height:"",alt:"",style:{maxHeight:"35px"}}))}}]),a}(n.Component);J.defaultProps={};var M=J,L=t(51),U=function(e){function a(e){var t;Object(c.a)(this,a),(t=Object(o.a)(this,Object(i.a)(a).call(this,e))).toggle=t.toggle.bind(Object(L.a)(Object(L.a)(t))),t.state={dropdownOpen:!1};var n=JSON.parse(sessionStorage.getItem("user_details"));return t.state={name:n.name,surname:n.surname,type:n.type,privacy:n.privacy},t}return Object(u.a)(a,e),Object(m.a)(a,[{key:"toggle",value:function(){this.setState({dropdownOpen:!this.state.dropdownOpen})}},{key:"dropNotif",value:function(){return l.a.createElement(f.j,{nav:!0,className:"d-md-down-none",isOpen:this.state.dropdownOpen,toggle:this.toggle},l.a.createElement(f.m,{nav:!0},l.a.createElement("i",{className:"icon-bell"}),l.a.createElement(f.a,{pill:!0,color:"danger"},5)),l.a.createElement(f.l,{right:!0},l.a.createElement(f.k,{header:!0,tag:"div",className:"text-center"},l.a.createElement("strong",null,"You have ",5," notifications")),l.a.createElement(f.k,null,l.a.createElement("i",{className:"icon-user-follow text-success"})," New user registered"),l.a.createElement(f.k,null,l.a.createElement("i",{className:"icon-user-unfollow text-danger"})," User deleted"),l.a.createElement(f.k,null,l.a.createElement("i",{className:"icon-chart text-info"})," Sales report is ready"),l.a.createElement(f.k,null,l.a.createElement("i",{className:"icon-basket-loaded text-primary"})," New client"),l.a.createElement(f.k,null,l.a.createElement("i",{className:"icon-speedometer text-warning"})," Server overloaded"),l.a.createElement(f.k,{header:!0,tag:"div",className:"text-center"},l.a.createElement("strong",null,"Server")),l.a.createElement(f.k,null,l.a.createElement("div",{className:"text-uppercase mb-1"},l.a.createElement("small",null,l.a.createElement("b",null,"CPU Usage"))),l.a.createElement(f.t,{className:"progress-xs",color:"info",value:"25"}),l.a.createElement("small",{className:"text-muted"},"348 Processes. 1/4 Cores.")),l.a.createElement(f.k,null,l.a.createElement("div",{className:"text-uppercase mb-1"},l.a.createElement("small",null,l.a.createElement("b",null,"Memory Usage"))),l.a.createElement(f.t,{className:"progress-xs",color:"warning",value:70}),l.a.createElement("small",{className:"text-muted"},"11444GB/16384MB")),l.a.createElement(f.k,null,l.a.createElement("div",{className:"text-uppercase mb-1"},l.a.createElement("small",null,l.a.createElement("b",null,"SSD 1 Usage"))),l.a.createElement(f.t,{className:"progress-xs",color:"danger",value:90}),l.a.createElement("small",{className:"text-muted"},"243GB/256GB"))))}},{key:"dropAccnt",value:function(){return l.a.createElement(f.j,{nav:!0,isOpen:this.state.dropdownOpen,toggle:this.toggle},l.a.createElement(f.m,{nav:!0},l.a.createElement("img",{src:"assets/img/avatars/avatar.png",className:"img-avatar",alt:""})),l.a.createElement(f.l,{right:!0,style:{width:"200px"}},l.a.createElement(f.k,{header:!0,tag:"div",className:"text-center"},l.a.createElement("strong",null,this.state.name," ",this.state.surname)),l.a.createElement(f.k,null,l.a.createElement("i",{className:"fa fa-lock"})," ",l.a.createElement("a",{href:"logout.jsp"},"Logout")),l.a.createElement(f.k,{header:!0,tag:"div",className:"text-center"},l.a.createElement("strong",null,"Terms and conditions")),l.a.createElement(f.k,null,l.a.createElement("span",{style:{fontSize:"10px"}},l.a.createElement("i",{className:"fa fa-check",style:{color:"green"}})," accepted on ",this.state.privacy))))}},{key:"dropTasks",value:function(){return l.a.createElement(f.j,{nav:!0,className:"d-md-down-none",isOpen:this.state.dropdownOpen,toggle:this.toggle},l.a.createElement(f.m,{nav:!0},l.a.createElement("i",{className:"icon-list"}),l.a.createElement(f.a,{pill:!0,color:"warning"},15)),l.a.createElement(f.l,{right:!0,className:"dropdown-menu-lg"},l.a.createElement(f.k,{header:!0,tag:"div",className:"text-center"},l.a.createElement("strong",null,"You have ",15," pending tasks")),l.a.createElement(f.k,null,l.a.createElement("div",{className:"small mb-1"},"Upgrade NPM & Bower ",l.a.createElement("span",{className:"float-right"},l.a.createElement("strong",null,"0%"))),l.a.createElement(f.t,{className:"progress-xs",color:"info",value:0})),l.a.createElement(f.k,null,l.a.createElement("div",{className:"small mb-1"},"ReactJS Version ",l.a.createElement("span",{className:"float-right"},l.a.createElement("strong",null,"25%"))),l.a.createElement(f.t,{className:"progress-xs",color:"danger",value:25})),l.a.createElement(f.k,null,l.a.createElement("div",{className:"small mb-1"},"VueJS Version ",l.a.createElement("span",{className:"float-right"},l.a.createElement("strong",null,"50%"))),l.a.createElement(f.t,{className:"progress-xs",color:"warning",value:50})),l.a.createElement(f.k,null,l.a.createElement("div",{className:"small mb-1"},"Add new layouts ",l.a.createElement("span",{className:"float-right"},l.a.createElement("strong",null,"75%"))),l.a.createElement(f.t,{className:"progress-xs",color:"info",value:75})),l.a.createElement(f.k,null,l.a.createElement("div",{className:"small mb-1"},"Angular 2 Cli Version ",l.a.createElement("span",{className:"float-right"},l.a.createElement("strong",null,"100%"))),l.a.createElement(f.t,{className:"progress-xs",color:"success",value:100})),l.a.createElement(f.k,{className:"text-center"},l.a.createElement("strong",null,"View all tasks"))))}},{key:"dropMssgs",value:function(){return l.a.createElement(f.j,{nav:!0,className:"d-md-down-none",isOpen:this.state.dropdownOpen,toggle:this.toggle},l.a.createElement(f.m,{nav:!0},l.a.createElement("i",{className:"icon-envelope-letter"}),l.a.createElement(f.a,{pill:!0,color:"info"},7)),l.a.createElement(f.l,{right:!0,className:"dropdown-menu-lg"},l.a.createElement(f.k,{header:!0,tag:"div"},l.a.createElement("strong",null,"You have ",7," messages")),l.a.createElement(f.k,{href:"#"},l.a.createElement("div",{className:"message"},l.a.createElement("div",{className:"pt-3 mr-3 float-left"},l.a.createElement("div",{className:"avatar"},l.a.createElement("img",{src:"assets/img/avatars/7.jpg",className:"img-avatar",alt:"admin@bootstrapmaster.com"}),l.a.createElement("span",{className:"avatar-status badge-success"}))),l.a.createElement("div",null,l.a.createElement("small",{className:"text-muted"},"John Doe"),l.a.createElement("small",{className:"text-muted float-right mt-1"},"Just now")),l.a.createElement("div",{className:"text-truncate font-weight-bold"},l.a.createElement("span",{className:"fa fa-exclamation text-danger"})," Important message"),l.a.createElement("div",{className:"small text-muted text-truncate"},"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt..."))),l.a.createElement(f.k,{href:"#"},l.a.createElement("div",{className:"message"},l.a.createElement("div",{className:"pt-3 mr-3 float-left"},l.a.createElement("div",{className:"avatar"},l.a.createElement("img",{src:"assets/img/avatars/6.jpg",className:"img-avatar",alt:"admin@bootstrapmaster.com"}),l.a.createElement("span",{className:"avatar-status badge-warning"}))),l.a.createElement("div",null,l.a.createElement("small",{className:"text-muted"},"Jane Doe"),l.a.createElement("small",{className:"text-muted float-right mt-1"},"5 minutes ago")),l.a.createElement("div",{className:"text-truncate font-weight-bold"},"Lorem ipsum dolor sit amet"),l.a.createElement("div",{className:"small text-muted text-truncate"},"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt..."))),l.a.createElement(f.k,{href:"#"},l.a.createElement("div",{className:"message"},l.a.createElement("div",{className:"pt-3 mr-3 float-left"},l.a.createElement("div",{className:"avatar"},l.a.createElement("img",{src:"assets/img/avatars/5.jpg",className:"img-avatar",alt:"admin@bootstrapmaster.com"}),l.a.createElement("span",{className:"avatar-status badge-danger"}))),l.a.createElement("div",null,l.a.createElement("small",{className:"text-muted"},"Janet Doe"),l.a.createElement("small",{className:"text-muted float-right mt-1"},"1:52 PM")),l.a.createElement("div",{className:"text-truncate font-weight-bold"},"Lorem ipsum dolor sit amet"),l.a.createElement("div",{className:"small text-muted text-truncate"},"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt..."))),l.a.createElement(f.k,{href:"#"},l.a.createElement("div",{className:"message"},l.a.createElement("div",{className:"pt-3 mr-3 float-left"},l.a.createElement("div",{className:"avatar"},l.a.createElement("img",{src:"assets/img/avatars/4.jpg",className:"img-avatar",alt:"admin@bootstrapmaster.com"}),l.a.createElement("span",{className:"avatar-status badge-info"}))),l.a.createElement("div",null,l.a.createElement("small",{className:"text-muted"},"Joe Doe"),l.a.createElement("small",{className:"text-muted float-right mt-1"},"4:03 AM")),l.a.createElement("div",{className:"text-truncate font-weight-bold"},"Lorem ipsum dolor sit amet"),l.a.createElement("div",{className:"small text-muted text-truncate"},"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt..."))),l.a.createElement(f.k,{href:"#",className:"text-center"},l.a.createElement("strong",null,"View all messages"))))}},{key:"render",value:function(){var e=this.props,a=e.notif,t=e.accnt,n=e.tasks,l=e.mssgs;return a?this.dropNotif():t?this.dropAccnt():n?this.dropTasks():l?this.dropMssgs():null}}]),a}(n.Component);U.defaultProps={notif:!1,accnt:!1,tasks:!1,mssgs:!1};var D=U,I=t(78),B=t.n(I),R=function(e){function a(){return Object(c.a)(this,a),Object(o.a)(this,Object(i.a)(a).apply(this,arguments))}return Object(u.a)(a,e),Object(m.a)(a,[{key:"render",value:function(){var e=this.props;e.children,Object(A.a)(e,["children"]);return l.a.createElement(l.a.Fragment,null,l.a.createElement(b.l,{className:"d-lg-none",display:"md",mobile:!0}),l.a.createElement(b.e,{full:{src:B.a,width:89,height:25,alt:"HateMeter Logo"},minimized:{src:B.a,width:30,height:30,alt:"HateMeter Logo"}}),l.a.createElement(b.l,{className:"d-md-down-none h-100 b-r-1",display:"lg"}),l.a.createElement(f.q,{className:"ml-auto",navbar:!0},l.a.createElement(f.r,{className:"d-md-down-none"}),l.a.createElement(D,{accnt:!0})))}}]),a}(n.Component);R.defaultProps={};var V=R,_=function(e){function a(e){var t;Object(c.a)(this,a),t=Object(o.a)(this,Object(i.a)(a).call(this,e));var n=JSON.parse(sessionStorage.getItem("user_details"));return t.state={name:n.name,surname:n.surname,type:n.type,avatar:n.avatar},t}return Object(u.a)(a,e),Object(m.a)(a,[{key:"render",value:function(){var e=this.props;e.children,Object(A.a)(e,["children"]);return l.a.createElement(l.a.Fragment,null,l.a.createElement("img",{src:"assets/img/avatars/"+this.state.avatar,className:"img-avatar",alt:"Avatar"}),l.a.createElement("div",null,l.a.createElement("strong",null,this.state.name," ",this.state.surname)),l.a.createElement("div",{className:"text-muted"},l.a.createElement("small",null,this.state.type)))}}]),a}(n.Component);_.defaultProps={};var F=_,G=function(e){function a(){return Object(c.a)(this,a),Object(o.a)(this,Object(i.a)(a).apply(this,arguments))}return Object(u.a)(a,e),Object(m.a)(a,[{key:"render",value:function(){return l.a.createElement("div",{className:"app"},l.a.createElement(b.d,{fixed:!0},l.a.createElement(V,null)),l.a.createElement("div",{className:"app-body"},l.a.createElement(b.f,{fixed:!0,display:"lg"},l.a.createElement(b.i,null,l.a.createElement(F,null)),l.a.createElement(b.h,null),l.a.createElement(b.k,Object.assign({navConfig:x},this.props)),l.a.createElement(b.g,null),l.a.createElement(b.j,null)),l.a.createElement("main",{className:"main"},l.a.createElement(b.b,{appRoutes:H}),l.a.createElement(f.i,{fluid:!0},l.a.createElement(p.a,null,H.map(function(e,a){return e.component?l.a.createElement(E.a,{key:a,path:e.path,exact:e.exact,name:e.name,render:function(a){return l.a.createElement(e.component,a)}}):null}),l.a.createElement(h.a,{from:"/",to:"/generalTrends"})))),l.a.createElement(b.a,{id:"appSideBar",fixed:!0})),l.a.createElement(b.c,null,l.a.createElement(M,null)),l.a.createElement(N.NotificationContainer,null))}}]),a}(n.Component),Y=(t(299),function(e){function a(e){var t;return Object(c.a)(this,a),t=Object(o.a)(this,Object(i.a)(a).call(this,e)),v.a.ajax({type:"POST",crossDomain:!0,url:window.HateMeterApiUrlPrefix+"userDetails",data:JSON.stringify(""),dataType:"json",async:!1,success:function(e){sessionStorage.setItem("user_details",JSON.stringify(e))},error:function(e,a){}}),t}return Object(u.a)(a,e),Object(m.a)(a,[{key:"render",value:function(){return l.a.createElement(d.a,null,l.a.createElement(p.a,null,l.a.createElement(E.a,{path:"/",name:"Home",component:G})))}}]),a}(n.Component));Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));window.HateMeterApiUrlPrefix="",v.a.ajax({type:"POST",crossDomain:!0,url:window.HateMeterApiUrlPrefix+"userDetails",data:JSON.stringify(""),dataType:"json",async:!1,success:function(e){null===e.privacy?window.location.replace("privacy.html"):s.a.render(l.a.createElement(Y,null),document.getElementById("root"))},error:function(e,a){}}),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then(function(e){e.unregister()})},78:function(e,a,t){e.exports=t.p+"static/media/logo.5b7c0088.svg"}},[[184,14,12]]]);
//# sourceMappingURL=main.bb11842f.chunk.js.map