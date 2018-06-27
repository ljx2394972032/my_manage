$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + '/sys/users/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '姓名', name: 'fullName', index: 'full_name', width: 80},
            {label: 'firstName', name: 'firstName', index: 'first_name', width: 80, hidden: true},
            {label: 'lastName', name: 'lastName', index: 'last_name', width: 80, hidden: true},
            {label: '邮箱', name: 'email', index: 'email', width: 80},
            {label: '手机号', name: 'phone', index: 'phone', width: 80},
            {
                label: '是否经销商', name: 'isDealer', index: 'is_dealer', width: 40,
                formatter: function (cellvalue) {
                    return cellvalue == 0 ? "个体" : "商户"
                }
            },
            {label: '地址', name: 'state', width: 150},
            {label: '地区', name: 'suburb', width: 80, hidden: true},
            {
                label: '状态', name: 'frozen', width: 80,
                formatter: function (cellvalue) {
                    return cellvalue == true ? "冻结" : "正常"
                }
            },
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            // merger("jqGrid", "firstName", "lastName");   //拼接 姓 名
            merger("jqGrid", "state", "suburb");   //拼接 地址
        }
    });
});


/**
 * @Author : ljx
 * @Description : 判断字符串是否为空
 * @Param :
 * @Date : 2018/4/27 11:15
 */
function isEmpty() {
    for (var i = 0; i < arguments.length; i++) {
        if (arguments[i].trim() == null || arguments[i].trim() == "") {
            return true;
        }
    }
}

//拼接 公共调用
function merger(gridName, CellName1, CellName2) {
    //得到显示到界面的id集合
    var mya = $("#" + gridName).getDataIDs();
    //当前显示多少条
    var length = mya.length;
    for (var i = 0; i < length; i++) {
        var rowData = $("#" + gridName + "").jqGrid('getRowData', mya[i]);
        var suburb = rowData[CellName2];
        var state = rowData[CellName1];
        var name = "";
        if (isEmpty(suburb) && isEmpty(state)) {  //都为空
            name = "";
        } else if (isEmpty(suburb) && !isEmpty(state)) {
            name = state;
        } else if (!isEmpty(suburb) && isEmpty(state)) {
            name = suburb;
        } else if (!isEmpty(suburb) && !isEmpty(state)) {
            name = suburb + "," + state;
        } else {
            name = "";
        }
        $("#" + gridName).setCell(mya[i], CellName1, name);
    }
}

var vm = new Vue({
        el: '#rapp',
        data: {
            showList: true,
            showDetail: false,
            showFrozen: false,
            title: null,
            users: {},
            key: null,
            state: null,
            suburb: null,
            isDealer: "",
            isFrozen: "",
            stateSuburb: [],   //州和城市
            suburbList: [],  //城市列表
        },
        mounted: function () {
            this.getAllState();
        },
        methods: {
            getAllState: function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "/sys/statesuburb/getAll",
                    contentType: "application/json",
                    data: {},
                    success: function (r) {
                        if (r.code == 0) {
                            vm.stateSuburb = r.data;
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            },
            //州的选项改变
            changeState: function () {
                var stateid = $("#stateList").val();
                if (stateid == null || stateid == "") {
                    vm.state = null;
                    vm.suburb = null;
                    vm.suburbList = [];
                } else {
                    vm.state = $("#stateList").find("option:selected").text();   //把取到的state赋值给vm
                }
                for (var i = 0; i < vm.stateSuburb.length; i++) {
                    if (vm.stateSuburb[i].id == stateid) {    //如果州的id相同
                        vm.suburbList = vm.stateSuburb[i].suburbs;
                    }
                }
            },
            //城市选项改变
            changeSuburb: function () {
                var suburb = $("#suburbList").val();
                if (suburb == null || suburb == "") {
                    vm.suburb = null;
                } else {
                    vm.suburb = $("#suburbList").find("option:selected").text();   //把取到的suburb赋值给vm
                }
            },
            //经销商改变
            changeDealer: function () {
                var dealer = $("#isDealer").val();
                dealer = dealer == "" ? null : dealer;
                vm.isDealer = dealer;
            },
            //冻结状态改变
            changeFrozen: function () {
                var frozen = $("#isFrozen").val();
                frozen = frozen == "" ? null : frozen;
                vm.isFrozen = frozen;
            },
            //冻结账号
            frozen: function (type) {
                var msg = type == 0 ? "解冻" : "冻结";
                confirm("确认要" + msg + "该用户吗?", function () {
                    vm.users.frozen = type;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "/sys/users/update",
                        contentType: "application/json",
                        data: JSON.stringify(vm.users),
                        success: function (r) {
                            if (r.code === 0) {
                                alert('操作成功', function (index) {
                                    vm.reload();
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                });
            },
            //设为个体用户
            downGrade: function () {
                confirm("确认要将该用户设为个体用户吗?", function () {
                    $.ajax({
                        type: "POST",
                        url: baseURL + "/sys/users/downGrade",
                        data: {
                            id: vm.users.id,
                        },
                        success: function (r) {
                            if (r.code === 0) {
                                alert('操作成功', function (index) {
                                    vm.reload();
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                });
            },
            exports: function () {
                var url = baseURL + "/sys/users/export?X-Token=" + token;
                window.location.href = url;
            },
            info: function () {
                var id = getSelectedRow();
                if (id == null) {
                    return;
                }
                vm.showFrozen = false;
                vm.showList = false;
                vm.showDetail = true;

                vm.title = "详情";

                vm.getInfo(id)
            },
            query: function () {
                vm.reload(1);
            },
            add: function () {
                vm.showFrozen = false;
                vm.showList = false;
                vm.showDetail = false;
                vm.title = "新增";
                vm.users = {};
            },
            update: function (event) {
                var id = getSelectedRow();
                if (id == null) {
                    return;
                }
                vm.showFrozen = true;
                vm.showList = false;
                vm.showDetail = false;
                vm.title = "修改";

                vm.getInfo(id)
            },
            saveOrUpdate: function (event) {
                var url = vm.users.id == null ? "/sys/users/save" : "/sys/users/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.users),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            },
            del: function (event) {
                var ids = getSelectedRows();
                if (ids == null) {
                    return;
                }

                confirm('确定要删除选中的记录？', function () {
                    $.ajax({
                        type: "POST",
                        url: baseURL + "/sys/users/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function (r) {
                            if (r.code == 0) {
                                alert('操作成功', function (index) {
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                });
            },
            getInfo: function (id) {
                $.ajax({
                    type: "GET",
                    url: baseURL + "/sys/users/info/" + id,
                    async: false,
                    success: function (r) {
                        vm.users = r.users;
                    }
                });
            },
            reload: function (event) {
                vm.showFrozen = false;
                vm.showList = true;
                vm.showDetail = false;
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                if (event == 1) {
                    page = 1;
                }
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: {
                        'key': vm.key,
                        'isDealer': vm.isDealer,
                        'frozen': vm.isFrozen,
                        'state': vm.state,
                        'suburb': vm.suburb
                    },
                    page: page
                }).trigger("reloadGrid");
            }
        }
    })
;