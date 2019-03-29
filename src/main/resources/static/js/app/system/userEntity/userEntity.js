$(function () {
    var $userTableForm = $(".user-table-form");
    var settings = {
        url: ctx + "userEntity/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                fname: $userTableForm.find("input[name='fname']").val().trim(),
                fisforbidden: $userTableForm.find("select[name='fisforbidden']").val()
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'fid',
            visible: false
        }, {
            field: 'fname',
            title: '用户名'
        }, {
            field: 'fdeforgunitname',
            title: '部门'
        }, {
        field: 'fcell',
        title: '手机'
        }, {
        field: 'fcreatetime',
        title: '创建时间'
        }, {
            field: 'fisforbidden',
            title: '状态',
            formatter: function (value, row, index) {
                if (value === '1') return '<span class="badge badge-success">有效</span>';
                if (value === '0') return '<span class="badge badge-warning">锁定</span>';
            }
        }

        ]
    };

    $MB.initTable('userTable', settings);
});

function search() {
    $MB.refreshTable('userTable');
}

function refresh() {
    $(".user-table-form")[0].reset();
    $MB.refreshTable('userTable');
}

function deleteUsers() {
    var selected = $("#userTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的用户！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].fid;
        if (i !== (selected_length - 1)) ids += ",";
        if ( userName === selected[i].fname) contain = true;
    }
    if (contain) {
        $MB.n_warning('勾选用户中包含当前登录用户，无法删除！');
        return;
    }

    $MB.confirm({
        text: "确定删除选中用户？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'userEntity/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportUserExcel() {
    $.post(ctx + "userEntity/excel", $(".user-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportUserCsv() {
    $.post(ctx + "userEntity/csv", $(".user-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}