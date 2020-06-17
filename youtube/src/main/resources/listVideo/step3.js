var window = {};

var itemsMain  = arr.onResponseReceivedCommands[0].appendContinuationItemsAction.continuationItems;


function nextFun() {
    try {
        var contents =  itemsMain
        var continuationItemRenderer = {};
        for (var contentsKey in contents) {
            var content = contents[contentsKey]
            if (content.continuationItemRenderer) {
                continuationItemRenderer = content.continuationItemRenderer.continuationEndpoint;
            }
        }
        var o = {};
        var context = {};
        context.clickTracking = {};
        context.user= {}
        context.clickTracking.clickTrackingParams = continuationItemRenderer.clickTrackingParams;
        o.continuation = continuationItemRenderer.continuationCommand.token;
        o.context = context;
        return JSON.stringify(o);
    } catch (e) {
        return ""
    }

}

function extractVideo(o, item) {
    o.videoId = item.videoId;
    try {

        o.name = item.title.runs[0].text
    } catch (e) {
        o.name = ""
    }
    try {

        o.imgSrc = item.thumbnail.thumbnails[0].url;
    } catch (e) {
        o.imgSrc = ""
    }
    var r = item.channelThumbnailSupportedRenderers.channelThumbnailWithLinkRenderer;
    var longBylineText = item.longBylineText;
    var newHeader = {};
    try {

        newHeader.name = longBylineText.runs[0].text
    } catch (e) {

    }
    //primaryContents.sectionListRenderer.contents[0].itemSectionRenderer.contents[0].videoRenderer.longBylineText
    try {

        newHeader.imgSrc = r.thumbnails[r.thumbnails.length - 1].url
    } catch (e) {
    }
    try {

        newHeader.channelId = r.navigationEndpoint.browseEndpoint.browseId
    } catch (e) {

    }

    o.channelInfo = newHeader;
}

function extractPlayList(o, item) {
    o.playlistId = item.playlistId;
    try {
        o.name = item.title.simpleText
    } catch (e) {
        o.name = ""
    }
    try {

        o.imgSrc = item.thumbnail.thumbnails[0].url;
    } catch (e) {
        o.imgSrc = ""
    }
}

function getList() {
    var arr = [];
    var items = itemsMain[0].itemSectionRenderer.contents
    for (itemKey in items) {
        var o = {};
        var item = items[itemKey].videoRenderer;
        if (item) {
            extractVideo(o, item);
        } else {
            item = items[itemKey].playlistRenderer;
            if (item) {
                extractPlayList(o, item);
            }
        }

        //shelfRenderer

        arr.push(o);
    }
    return JSON.stringify(arr)
}
