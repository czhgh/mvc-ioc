function $(id) {
    return document.getElementById(id);
}

window.onload = function () {
    var tblFruit = $("tbl_fruit");
    var trs = tblFruit.rows;
    for (var i = 1; i < trs.length; i++) {
        var tr = trs[i];
        bindEvent(tr);
    }

}

function page(pageNo){
    window.location.href='index?pageNo='+pageNo;
}

function delFruit(fId) {
    if (confirm("确定要删除这个水果嘛?")) {
        window.location.href = 'delFruit.do?fId=' + fId;
    }
}


function bindEvent(tr) {
    tr.onmouseover = showBGColor;
    tr.onmouseout = clearBGColor;
}


function showBGColor() {
    if(event && event.srcElement && event.srcElement.tagName=="TD" ){
        var td = event.srcElement;
        var tr = td.parentElement;
        tr.style.backgroundColor="#ffcc66";

        var tds = tr.cells;
        for(var i = 0;i<tds.length;i++){
            tds[i].style.color="#cc00ff"
        }
    }
}

function clearBGColor() {
    if(event && event.srcElement && event.srcElement.tagName=="TD" ){
        var td = event.srcElement;
        var tr = td.parentElement;
        tr.style.backgroundColor="#cccc99";

        var tds = tr.cells;
        for(var i = 0;i<tds.length;i++){
            tds[i].style.color="#808080"
        }
    }

}




