$ ->
    initialLoad = ->
        updateTime()

        for i in [0..6]
            $('#tableSummary').append $("<tr>").attr 'id', 'header-'+i
            $('#tableSummary').append $("<tr>").attr 'id', 'bid-'+i
            $('#tableSummary').append $("<tr>").attr 'id', 'bidder-'+i
            if i < 5
                $('#tableSummary').append $("<tr>").attr {'id': 'blank-'+i, 'class': 'blank'}
                $('#blank-'+i).append $("<td>").attr 'class', 'blank'

        $.get "/getResults", (slotSummaries) ->
            $.each slotSummaries, (index, slotSummary) ->
                position = Math.floor((slotSummary.slot-1)/5)

                $('#header-'+position).append $("<th>").attr 'id', 'slot-'+slotSummary.slot
                $('#slot-'+slotSummary.slot).text(slotSummary.slot)

                $('#bid-'+position).append $("<td>").attr 'id', 'maxBid-'+slotSummary.slot
                $('#maxBid-'+slotSummary.slot).text('$'+slotSummary.maxBid)

                $('#bidder-'+position).append $("<td>").attr 'id', 'maxBidders-'+slotSummary.slot
                $('#maxBidders-'+slotSummary.slot).html(slotSummary.maxBidders.join ", ")
                $('#maxBidders-'+slotSummary.slot).prop('title', slotSummary.maxIDs.join ",")

                colorSlot(slotSummary)

                if slotSummary.slot % 5 > 0
                    $('#header-'+position).append $("<th>").attr 'class', 'blank'
                    $('#bid-'+position).append $("<th>").attr 'class', 'blank'
                    $('#bidder-'+position).append $("<th>").attr 'class', 'blank'

    reloadTable = ->
        updateTime()

        $.get "/getResults", (slotSummaries) ->
            $.each slotSummaries, (index, slotSummary) ->
                willHighlight = false

                currentMaxBid = $('#maxBid-'+slotSummary.slot).text()
                if (currentMaxBid != '$'+slotSummary.maxBid)
                    $('#maxBid-'+slotSummary.slot).text('$'+slotSummary.maxBid)
                    willHighlight = true

                $('#maxBidders-'+slotSummary.slot).html(slotSummary.maxBidders.join ", ")
                $('#maxBidders-'+slotSummary.slot).prop('title', slotSummary.maxIDs.join ",")

                if (willHighlight)
                    $('#maxBid-'+slotSummary.slot).effect("highlight", {}, 2000)
                    $('#maxBidders-'+slotSummary.slot).effect("highlight", {}, 2000)

                colorSlot(slotSummary)

        setTimeout reloadTable, 5000

    updateTime = ->
        now = new Date
        $('#time').html((now.getDate()+'/'+(now.getMonth()+1)+'/'+now.getFullYear()+' '+now.getHours()+':'+now.getMinutes()+':'+now.getSeconds()).replace /\b(\d)\b/g, '0$1')

    colorSlot = (slotSummary) ->
        if slotSummary.maxBid == 0
            $('#slot-'+slotSummary.slot).attr('type', 'NO_BIDDERS')
        else
            if slotSummary.maxBidders.length == 1
                if slotSummary.maxBid >= 300
                    $('#slot-'+slotSummary.slot).attr('type', 'DONE')
                else
                    $('#slot-'+slotSummary.slot).attr('type', 'LOW')
            else
                if slotSummary.maxBidders.length > 2
                    $('#slot-'+slotSummary.slot).attr('type', 'MULTIPLE')
                    $('#maxBidders-'+slotSummary.slot).text(slotSummary.maxBidders.length + ' bidders')
                    $('#maxBidders-'+slotSummary.slot).attr('title', slotSummary.maxBidders)
                else
                    $('#slot-'+slotSummary.slot).attr('type', 'COMPETE')

    initialLoad()
    setTimeout reloadTable, 5000