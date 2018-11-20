/**
 * Created by zzl on 2017-07-01.
 */

(function () {

    function Grid($container, columns, dataSourceUrl, dataSourceParams) {
        var me = this;
        if(typeof(dataSourceUrl) === 'object' && dataSourceParams === undefined) {
            dataSourceParams = dataSourceUrl;
            dataSourceUrl = null;
        }

        $container = $($container);

        me.$container = $container;
        me.$grid = $container.find('.g-grid');
        me.$pager = $container.find('.g-pager');

        if(me.$grid.hasClass('numbered')) {
            me.numbered = true;
        }
        if(me.$grid.hasClass('checkable')) {
            me.checkable = true;
        }
        if(me.$grid.hasClass('dblclick')) {
            me.dblclick = true;
        }
        if(me.$grid.hasClass('single-select')) {
            me.singleSelect = true;
        }
        if(me.$grid.hasClass('keep-selection')) {
            me.keepSelection = true;
            me.allSelectedIds = [];
        }

        //
        for(var n = 0; n < columns.length; n++) {
            if(columns[n].editable) {
                me.editable = true;
                break;
            }
        }

        //
        columns = me._fixColumns(columns);
        me.columns = columns;

        me.fixedWidthFlags = me.collectFixedWidthFlags();

        me.dataSourceUrl = dataSourceUrl || 'data_source.json';
        me.dataSourceFields = _buildFields(columns);

        me.sortCols = [];

        me.buildDataSource();
        me.setupGrid();
        me.setupPager();

        me.params = dataSourceParams || {};
        //me.dataSource.read();

        // $(document).on('click.grid', function (e) {
        //     if(me.focusingEditor && e.target !== me.focusingEditor) {
        //         $(me.focusingEditor).trigger('blur-x');
        //     }
        // });
    }

    Grid.prototype.collectFixedWidthFlags = function () {
        var me = this;
        var flags = [];
        for(var n = 0; n < me.columns.length; n++) {
            flags.push(!!me.columns[n].width);
        }
        return flags;
    };

    Grid.prototype.refresh = function () {
        var me = this;
        // me.params = me.lastParams;
        me.cancelEditing();

        var selectedIds = me.getSelectedIds();
        me.dataSource.read(me.lastParams);
        me.selectedIdsToRestore = selectedIds;
    };

    Grid.prototype.restoreSelectedIds = function () {
        var me = this;
        var selectedIds = me.selectedIdsToRestore;
        delete me.selectedIdsToRestore;
        if(!selectedIds || !selectedIds.length) {
            return;
        }
        for(var n = 0; n < selectedIds.length; n++) {
            var $chk = me.$grid.find('input[value=' + selectedIds[n] + ']');
            $chk.prop('checked', true);
            $chk.closest('tr').addClass('selected');
        }
    };

    Grid.prototype.getRowCount = function () {
        var me = this;
        return me.$grid.find('.k-grid-content').find('tr').length;
    };

    Grid.prototype.getSelectedIds = function () {
        var me = this;
        if(me.keepSelection) {
            me.saveSelection();
            return me.allSelectedIds;
        }
        var ids = [];
        me.$grid.find('.check-row:checked').each(function () {
            ids.push($(this).val());
        });
        return ids;
    };

    Grid.prototype.getSelectedData = function () {
        var me = this;
        return me.getSelectedDatas()[0];
    };

    Grid.prototype.getSelectedDatas = function () {
        var me = this;
        var datas = [];
        me.$grid.find('tr.selected').each(function () {
            datas.push(me.dataSource.at($(this).index()));
        });
        return datas;
    };
    Grid.prototype.focus = function (index,field) {
        $('.g-grid tr.selected td')[index].click();
    };

    Grid.prototype.getAllDatas = function () {
        var me = this;
        var datas = [];
        for(var i=0;i<me.dataSource.length;i++){
            datas.push(me.dataSource.at(i))
        }
        return me.dataSource.data();
    };

    Grid.prototype.updateParams = function (params) {
        var me = this;
        me.params = params;
        me.dataSource.read();
    };

    Grid.prototype._fixColumns = function (columns) {
        var me = this;
        var newColumns = [];
        if(me.numbered) {
            newColumns.push({
                field: '_rn',
                headerTemplate: '<div class="rn-head"></div>',
                width: 29,
                locked: true,
                type: 'number'
            });
        }

        if(me.checkable) {
            newColumns.push({
                headerTemplate: '<input type="checkbox" class="check-all"/>',
                template: function (item) {
                    return '<input type="checkbox" class="check-row" ' +
                        ' value="' + item.id + '"/>';
                }
            });
        }

        for(var n = 0; n < columns.length; n++) {
            var column = columns[n];
            var field = column.field;
            column.oriType = column.type;
            if(column.type === 'date') {
                column.template = "#=" + field + "&& kendo.toString(" +
                    "new Date(" + field + "), 'yyyy-MM-dd') || ''#";
                column.type = 'number'
            }
            else if(column.type === 'datetime') {
                column.template = "#=" + field + "&& kendo.toString(" +
                    "new Date(" + field + "), 'yyyy-MM-dd HH:mm') || ''#";
                column.type = 'number';
            }
            newColumns.push(column);
        }
        return newColumns;
    };

    function _buildFields(columns) {
        var me = this;
        var fields = {};
        for(var n = 0; n < me.columns.length; n++) {
            var column = me.columns[n];
            if(!column.field) {
                continue;
            }
            fields[column.field] = {
                type: column.type || 'string'
            }
        }
        return fields;
    }

    Grid.prototype.showEditor = function ($tr, $td, rowIndex, colIndex) {
        var me = this;
        var column = me.columns[colIndex];

        var $editor;
        var row = me.dataSource.at(rowIndex);
        var value = row[column.field] ;

        if(column.editor) {
            $editor = $(column.editor(row, rowIndex));
        }
        else if(column.oriType === 'datetime' || column.oriType === 'date') {
            var format = 'yyyy-MM-dd';

            if(column.oriType === 'datetime') {
                format += ' HH:mm';
            }
            $editor = $('<input type="text" class="Wdate" ' +
                'onfocus="WdatePicker({dateFmt:\'' + format + '\'});"/>');
            if(value){
                value = new Date(value).format(format);
            }
        }
        else {
            var inputType = 'text';
            if(column.type === 'number' && !column.readonly) {
                inputType = 'number';
            }
            $editor = $('<input type="' + inputType + '"/>');
        }
        $editor.addClass('cell-editor');
        if(column.readonly) {
            $editor.attr('readonly', 'readonly');
        }
        //
        //$td.css('position', 'relative');
        $td.addClass('editing');
        // var value = $td.text();
        // if(column.customBind) {
            //
        // }
        //else
        if($editor.is('select[multiple]')) {
            $editor.val(value.split(','));
        }
        else {
            $editor.val(value);
        }
        $td.text('');
        $td.append($editor);

        /*
        $editor.on('blur-x', function () {
            var id = $tr.find('.check-row').val();
            var value = window.commitCellEdit(
                id, column.field, $editor.val(), rowIndex, colIndex);
            if(value !== false) {
                me.dataSource.at(rowIndex)[column.field] = value;
                $td.text(value);
                $editor.remove();
                $td.append($editor);
            }
        });
        //*/
        // $editor.on('focus', function () {
        //     me.focusingEditor = this;
        // });

        return $editor;
    };

    Grid.prototype.cancelEditing = function () {
        var me = this;
        if(!me.editing) {
            return;
        }
        me.$grid.find('td.editing').removeClass('editing');
        $(me.editors).remove();
        me.editing = false;
    };

    Grid.prototype.endEditing = function () {
        var me = this;
        if(!me.editing) {
            return;
        }

        for(var n = 0; n < me.editors.length; n++) {
            var $editor = $(me.editors[n]);
            if(!$.contains(document.body, $editor[0])) {
                //?
                break;
            }

            var $td = $editor.closest('td');
            var colIndex = $td.index() + (me.numbered? 1: 0);
            var column = me.columns[colIndex];

            var $tr = $td.parent();
            var rowIndex = $tr.index();

            var id = $tr.find('.check-row').val();
            var inputValue = $editor.val();
            if(column.oriType === 'number') {
                inputValue = Number(inputValue);
            }
            var value = inputValue;
            if(window.commitCellEdit) {
                value = window.commitCellEdit(id, column.field, inputValue, rowIndex, colIndex);
            }
            if(value !== false) {
                me.dataSource.at(rowIndex)[column.field] = value;
                if(typeof(column.template) === 'function') {
                    var row = me.dataSource.at(rowIndex);
                    $td.text(column.template(row));
                }
                else {
                    $td.text(value);
                }
            }
        }

        //
        me.$grid.find('td.editing').removeClass('editing');
        $(me.editors).remove();
        me.editing = false;
    };

    Grid.prototype.onRowClick = function ($tr, $tdClicked) {
        var me = this;
        if(!me.editable) {
            return;
        }
        if(!$tdClicked.length) {
            return;
        }
        var colIndex = $tdClicked.index() + (me.numbered? 1: 0);
        var rowIndex = $tr.index();
        //var $gridContent = me.$grid.find('.k-grid-content');
        //alert('' + rowIndex + ',' + colIndex);
        me.endEditing();
        me.editors = [];
        for(var n = 0; n < me.columns.length; n++) {
            var column = me.columns[n];
            if(!column.editable) {
                continue;
            }
            var $td = $tr.children('td').eq(n - (me.numbered? 1: 0));
            var $editor = me.showEditor($tr, $td, rowIndex, n);
            me.editors.push($editor[0]);
        }
        $tdClicked.find('input,select').focus();
        me.editing = true;
    };

    Grid.prototype._autoFitColumns = function () {
        var me = this;

        var columnIndex0 = 0;
        if(me.numbered && me.dataSource.total() > 99) {
            me.grid.autoFitColumn(0);
            columnIndex0 = 1;
        }

        for (var n = columnIndex0; n < me.columns.length; n++) {
            if(!me.fixedWidthFlags[n]) {
                me.grid.autoFitColumn(n);
            }
        }
    };

    Grid.prototype.autoFitColumns = function () {
        var me = this;
        if(me.$grid.width() < 20) {
            setTimeout(function () {
                me.autoFitColumns();
            }, 10);
            return;
        }
        // alert(me.$grid.width() + '@' + window.location);
        me._autoFitColumns();
        function checkBadWidths() {
            var badWidthCount = 0;
            var $ths = me.$grid.find('.k-grid-header-wrap').find('th');
            for(var n = 0; n < $ths.length; n++) {
                if($ths.eq(n).width() < 20) {
                    ++badWidthCount;
                    if(badWidthCount > 1) {
                        break;
                    }
                }
            }
            if(badWidthCount > 1) {
                setTimeout(function () {
                    me._autoFitColumns();
                }, 10);
            }
        }
        setTimeout(function () {
            checkBadWidths();

            // setTimeout(function () {
            //     checkBadWidths();
            // }, 100);
        }, 0);
    };

    Grid.prototype.setupGrid = function () {
        var me = this;

        me.$grid.addClass('no-vscroll no-hscroll');

        me.$grid.kendoGrid({
            dataSource: me.dataSource,
            autoBind: false,
            height: 30,
            filterable: false,
            sortable: false,
            resizable: true,
            columns: me.columns,
            dataBound: function (e) {

                me.autoFitColumns();
            }
        });
        me.grid = me.$grid.data('kendoGrid');
        var $ths = me.$grid.find('.k-grid-header-wrap th');
        for (var n = 1; n < me.columns.length; n++) {
            if (me.columns[n].sortable) {
                var colIndex = n;
                if(me.numbered) {
                    colIndex -= 1;
                }
                $ths.eq(colIndex).addClass('sortable');
            }
        }

        //
        me.$grid.on('click', '.sortable', function () {
            var $th = $(this);
            var sortCols = me.sortCols;
            var colIndex = $th.index() + 1;
            if ($th.hasClass('asc')) {
                $th.addClass('desc');
                $th.removeClass('asc');
                pub.replace(sortCols, colIndex, -colIndex);
            }
            else if ($th.hasClass('desc')) {
                $th.removeClass('desc');
                pub.remove(sortCols, -colIndex);
            }
            else {
                $th.addClass('asc');
                sortCols.push(colIndex);
            }
            me.dataSource.read();
        });

        //
        if(me.checkable) {
            me.$checkAll = me.$grid.find('.check-all');
            var $gridContent = me.$grid.find('.k-grid-content');
            $gridContent.on('click', function (e) {
                var target = e.target;

                if($(target).hasClass('check-row')) {
                    if(me.singleSelect && target.checked) {
                        $gridContent.find('tr.selected').each(function () {
                            $(this).removeClass('selected');
                            $(this).find('.check-row').prop('checked', false);
                        });
                    }
                    $(target).closest('tr').toggleClass('selected', target.checked);
                    return;
                }

                var nodeName = target.nodeName;
                if(nodeName === "A" || target.parentNode.nodeName === 'A' ||
                    nodeName === 'INPUT' || nodeName === 'SELECT' ||
                    nodeName === 'OPTION') {
                    return;
                }
                var $tr = $(target).closest('tr');
                if(!$tr.length) {
                    me.endEditing();
                    return;
                }
                var $chks = $gridContent.find('.check-row');
                if(me.editable && !me.singleSelect) {
                    //do nothing
                }
                else if(me.singleSelect) {
                    $chks.prop('checked', false);
                    $gridContent.find('tr.selected').removeClass('selected');
                    $tr.addClass('selected');
                    $tr.find(':checkbox').prop('checked', true);
                }
                else {
                    $tr.toggleClass('selected');
                    if ($tr.hasClass('selected')) {
                        $tr.find(':checkbox').prop('checked', true);
                    }
                    else {
                        $tr.find(':checkbox').prop('checked', false);
                    }
                    //
                    var checkedCount = $chks.filter(':checked').length;
                    me.$checkAll.prop('checked', checkedCount === $chks.length);
                }
                me.onRowClick($tr, $(target).closest('td'));
            });

            me.$checkAll.click(function () {
                if(me.singleSelect) {
                    this.checked =false;
                    return;
                }
                var $chks = $gridContent.find('.check-row');
                $chks.prop('checked', this.checked);
                $gridContent.find('tr').toggleClass('selected', this.checked);
            });
        }
        if(!me.editable && me.dblclick){
            var actionFn = window['onAction_dblclick'];
            if(!actionFn) {
                alert('onAction_dblclick处理方法');
            }else{
                var $gridContent = me.$grid.find('.k-grid-content');
                $gridContent.on('dblclick',function (e) {
                    var target = e.target;
                    var $tr = $(target).closest('tr');
                    var $td = $(target).closest('td');
                    if(!$tr.length) {
                        me.endEditing();
                        return;
                    }
                    if(!$td.length) {
                        return;
                    }
                    var colIndex = $td.index() + (me.numbered? 1: 0);
                    var rowIndex = $tr.index();
                    var row = me.dataSource.at(rowIndex);

                    actionFn(row);
                });
            }
        }
    };

    Grid.prototype.updateCell = function (rowIndex, colIndex, value) {
        var me = this;
        var $gridContent = me.$grid.find('.k-grid-content');
        var tdIndex = colIndex - (me.numbered? 1: 0);
        var field = me.columns[colIndex].field;
        if(value === undefined) {
            value = me.dataSource.at(rowIndex)[field];
        }
        $gridContent.find('tr').eq(rowIndex).find('td').eq(tdIndex).text(value);
    };

    Grid.prototype.setupPager = function () {
        var me = this;
        me.$pager.find('a').click(function () {
            var $a = $(this);
            if ($a.hasClass('disabled')) {
                return;
            }
            if ($a.hasClass('lnk-refresh')) {
                me.refresh();
                return;
            }
            var pageNo = me.$pager.data('pageNo');
            if (!pageNo) {
                return;
            }
            if ($a.hasClass('lnk-first')) {
                pageNo = 1;
            }
            else if ($a.hasClass('lnk-prev')) {
                --pageNo;
            }
            else if ($a.hasClass('lnk-next')) {
                ++pageNo;
            }
            else if ($a.hasClass('lnk-last')) {
                pageNo = 0;
            }
            var params = {
                page: pageNo
            };
            me.dataSource.read(params);
        });
        me.$pager.find('.page-size').change(function () {
            me.dataSource.pageSize($(this).val());
        });
        me.$pager.find('.page-no').on('keyup', function (e) {
            if(e.keyCode === 13) {
                var pageNo = Number($(this).val()) || 1;
                me.dataSource.read({
                    page: pageNo
                });
            }
        });
    };

    Grid.prototype.updatePager = function (queryResult) {
        var me = this;
        var $pager = me.$pager;

        var pageNo = queryResult.pageNo;
        var pageCount = queryResult.pageCount;
        var pageSize = queryResult.pageSize;
        $pager.find('.page-size').val(pageSize);
        $pager.find('.lnk-first, .lnk-prev').toggleClass('disabled', pageNo < 2);
        $pager.find('.lnk-next, .lnk-last').toggleClass('disabled', pageNo === pageCount);
        $pager.find('input').val(pageNo);
        $pager.find('.page-count').text(pageCount);

        var fromRowNo = pageNo == 0? 0: (pageNo - 1) * pageSize + 1;
        queryResult.fromRowNo = fromRowNo;
        $pager.find('.from-row-no').text(fromRowNo);

        var toRowNum = pageNo == 0? 0: fromRowNo + queryResult.rows.length - 1;
         queryResult.toRowNo = toRowNum;
        $pager.find('.to-row-no').text(toRowNum);

        $pager.find('.row-count').text(queryResult.rowCount);
        $pager.data('pageNo', pageNo);
    };

    Grid.prototype.buildSort = function() {
        var me = this;
        var sort = '';
        for (var n = 0; n < me.sortCols.length; n++) {
            var sortCol = me.sortCols[n];
            var desc = false;
            if (sortCol < 0) {
                sortCol = -sortCol;
                desc = true;
            }
            var field = me.columns[sortCol].field;
            if (n > 0) {
                sort += ',';
            }
            sort += field;
            if (desc) {
                sort += ' desc';
            }
        }
        return sort;
    };

    Grid.prototype.saveSelection = function () {
        var me = this;
        me.$grid.find('.check-row').each(function () {
            var checked = $(this).prop('checked');
            var id = $(this).val();
            var index = $.inArray(id, me.allSelectedIds);
            if(checked) {
                if(index !== -1) {
                    //ignore existing
                }
                else {
                    me.allSelectedIds.push(id);
                }
            }
            else {
                if(index === -1) {
                    //ignore not existing
                }
                else {
                    me.allSelectedIds.splice(index, 1);
                }
            }
        });
    };

    Grid.prototype.restoreSelection = function () {
        var me = this;
        me.$grid.find('.check-row').each(function () {
            var $chk = $(this);
            var id = $chk.val();
            var index = $.inArray(id, me.allSelectedIds);
            if(index === -1) {
                //not checked
            }
            else {
                $chk.prop('checked', true);
                $chk.closest('tr').addClass('selected');
            }
        });
    };

    Grid.prototype.getExportParams = function () {
        var me = this;
        var colTitles = [];
        var colNames = [];
        for(var n = 0; n < me.columns.length; n++) {
            var column = me.columns[n];
            if(!column.field || column.field === '_rn') {
                continue;
            }
            colTitles.push(column.title);
            colNames.push(column.field);
        }
        return $.extend({},
            {
                colTitles: colTitles.join(','),
                colNames: colNames.join(',')
            },
            me.lastParams);
    };

    Grid.prototype.buildDataSource = function () {
        var me = this;
        var $grid = me.$grid;

        function gotQueryResult(options, queryResult) {
            me.updatePager(queryResult);
            options.success(queryResult);
            setTimeout(function () {
                var $content = $grid.find('.k-grid-content');
                var noVscroll = $content[0].scrollHeight <= $content.height();
                var noHscroll = $content[0].scrollWidth <= $content.width();
                var oldNoVscroll = $grid.hasClass('no-vscroll');
                var oldNoHscroll = $grid.hasClass('no-hscroll');
                if(noVscroll !== oldNoVscroll || noHscroll !== oldNoHscroll) {
                    $grid.toggleClass('no-vscroll', noVscroll);
                    $grid.toggleClass('no-hscroll', noHscroll);
                    $grid.data('kendoGrid').refresh();
                }
                if(me.keepSelection) {
                    me.restoreSelection();
                }
                else {
                    me.restoreSelectedIds();
                }
            }, 0)
        }

        var dataSource = new kendo.data.DataSource({
            transport: {
                read: function (options) {
                    var url = me.dataSourceUrl;
                    var sort = me.buildSort();
                    var params = $.extend({}, me.lastParams,
                        options.data, me.params, {
                        sort: sort
                    });
                    if(me.numbered) {
                        params.generateRowNo = 1;
                    }
                    me.lastParams = params;

                    if(me.keepSelection) {
                        me.saveSelection();
                    }
                    if(window.customDataSourceQuery) {
                        customDataSourceQuery(function (queryResult) {
                            gotQueryResult(options, queryResult);
                        });
                    }
                    else {
                        pub.getJSON(url, params, function (result) {
                            var queryResult = result.data;
                            gotQueryResult(options, queryResult);
                        }, function (json) {
                            pub.jsonFailure(json);
                            options.error({});
                        });
                    }
                }
            },
            schema: {
                data: "rows",
                total: "rowCount",
                model: {
                    fields: me.dataSourceFields
                }
            },
            pageSize: me.$grid.data('pageSize') || 20,
            serverPaging: true,
            serverFiltering: true,
            serverSorting: true
        });
        me.dataSource = dataSource;
    };

    window.Grid = Grid;

})();

Date.prototype.format =function(format)
{
    var o = {
        "M+" : this.getMonth()+1,   //month
        "d+" : this.getDate(),      //day
        "h+" : this.getHours(),     //hour
        "m+" : this.getMinutes(),   //minute
        "s+" : this.getSeconds(),   //second
        "q+" : Math.floor((this.getMonth()+3)/3),   //quarter
        "S" : this.getMilliseconds()    //millisecond
    };
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
        (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length==1? o[k] :
                ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
};