function isReset() {
    return confirm("你确定重置吗?");
}

function validateForm() {
    var name = document.querySelector("input[name='fName']").value;
    var price = document.querySelector("input[name='price']").value;
    var count = document.querySelector("input[name='fCount']").value;
    var remark = document.querySelector("input[name='remark']").value;
    if (name == "" || name == null) {
        alert("名称不可以为空")
        return false;
    }
    if (price == "" || price == null) {
        alert("单价不可以为空")
        return false;
    }
    if (count == "" || count == null) {
        alert("小计不可以为空")
        return false;
    }
    if (remark == "" || remark == null) {
        alert("备注不可以为空")
        return false;
    }
    return true;
}

function $(Eleid) {
    return document.getElementById("Eleid")
}