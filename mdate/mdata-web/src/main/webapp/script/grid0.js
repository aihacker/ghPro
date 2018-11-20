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

    Grid.prototype.getSelectedDatas = function () {
        var me = this;
        var datas = [];
        me.$grid.find('tr.selected').each(function () {
            datas.push(me.dataSource.at($(this).index()));
        });
        return datas;
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
                // title: ' ',
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
            if(column.type === 'date') {
                column.template = "#=" + field + "&& kendo.toString(" +
                    "new Date(" + field + "), 'yyyy-MM-dd') || ''#";
                column.type = 'number'
            }
            else if(column.type === 'datetime') {
                column.template = "#=" + field + "&& kendo.toString(" +
                    "new Date(" + field + "), 'yyyy-MM-dd HH:mm') || ''#";
                column.type = 'number'
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

                var columnIndex0 = 0;
                if(me.numbered && me.dataSource.total() > 99) {
                    // if(queryResult.rowCount > 99) {
                        //?
                        me.grid.autoFitColumn(0);
                        columnIndex0 = 1;
                    // }
                    // else {
                    //
                    // }

                }

                for (var n = columnIndex0; n < this.columns.length; n++) {
                    // if(!this.columns[n].width) {
                    if(!me.fixedWidthFlags[n]) {
                        this.autoFitColumn(n);
                    }
                }
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
                if(target.nodeName === "A" || target.parentNode.nodeName === 'A' ||
                    target.nodeName === 'INPUT' && target.className !== 'check-row') {
                    return;
                }
                var $td = $(target).closest('td[role="gridcell"]');
                if($td.length) {
                    var colIndex = $td.index() + (me.numbered? 1: 0);
                    var column = me.columns[colIndex];
                    if(column.editable) {
                        var inputType = 'text';
                        if(column.type === 'number') {
                            inputType = 'number';
                        }
                        var $editor = $('<input type="' + inputType + '" class="cell-editor"/>');
                        $td.css('position', 'relative');
                        $editor.val($td.text());
                        $td.append($editor);
                        $editor.focus();
                        $editor.on('blur', function () {
                            //return;
                            var $tr = $td.closest('tr');
                            var id = $tr.find('.check-row').val();
                            var rowIndex = $tr.index();
                            var value = window.commitCellEdit(
                                id, column.field, $editor.val(), rowIndex, colIndex);
                            if(value !== false) {
                                me.dataSource.at(rowIndex)[column.field] = value;
                                $td.text(value);
                            }
                            $editor.remove();
                            $td.css('position', '');
                        });
                        return;
                    }
                }
                var $tr = $(target).closest('tr');
                if(!$tr.length) {
                    return;
                }
                var $chks = $gridContent.find('.check-row');
                if(me.singleSelect) {
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
            });
            me.$checkAll.click(function () {
                if(me.singleSelect) {
                    this.checked =false;
                    return;
                }
                var $chks = $gridContent.find('.check-row');
                $chks.prop('checked', this.checked);
                $gridContent.find('tr').toggleClass('selected', this.checked);
                //$(document).trigger('checkedChange', [this.checked? $chks.length: 0]);
            });
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
            else if ($a.hasClass('lnk-refresh')) {
                me.refresh();
                return;
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

        var fromRowNo = (pageNo - 1) * pageSize + 1;
        queryResult.fromRowNo = fromRowNo;
        $pager.find('.from-row-no').text(fromRowNo);

        var toRowNum = fromRowNo + queryResult.rows.length - 1;
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

    Grid.prototype.buildDataSource = function () {
        var me = this;
        var $grid = me.$grid;

        function gotQueryResult(options, queryResult) {
            me.updatePager(queryResult);
            // var $content = $grid.find('.k-grid-content');
            // var rowsHeight = (queryResult.toRowNo - queryResult.fromRowNo + 1) * 25;
            // if(rowsHeight > $content.height() - 2) {
            //     $grid.removeClass('no-vscroll');
            // }
            // else {
            //     $grid.addClass('no-vscroll');
            // }
            options.success(queryResult);
            setTimeout(function () {
                /*
                var $content = $grid.find('.k-grid-content');
                if ($content[0].scrollHeight > $content.height()) {
                    $grid.removeClass('no-vscroll');
                }
                else {
                    $grid.addClass('no-vscroll');
                }
                if ($content[0].scrollWidth > $content.width()) {
                    $grid.removeClass('no-hscroll');
                }
                else {
                    $grid.addClass('no-hscroll');
                }
                */
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

                // $grid.data('kendoGrid').refresh();
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
            pageSize: 20,
            serverPaging: true,
            serverFiltering: true,
            serverSorting: true
        });
        me.dataSource = dataSource;
    };

    window.Grid = Grid;

})();