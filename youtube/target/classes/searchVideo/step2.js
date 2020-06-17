var gridRenderer = window["ytInitialData"].contents.twoColumnSearchResultsRenderer.primaryContents
    .sectionListRenderer
    .contents[0]
    .itemSectionRenderer;
var items = gridRenderer
    .contents;


function nextFun() {
    try {
        var contents =  window["ytInitialData"].contents.twoColumnSearchResultsRenderer.primaryContents.sectionListRenderer.contents
        var continuationItemRenderer = {};
        for (var contentsKey in contents) {
            var content = contents[contentsKey]
            if (content.continuationItemRenderer) {
                continuationItemRenderer = content.continuationItemRenderer.continuationEndpoint;
            }
        }
        var o = {};
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

        o.publishedTimeText = item.publishedTimeText.simpleText
    } catch (e) {
        o.publishedTimeText = ""
    }
    try {

        o.viewCountText = item.viewCountText.simpleText
    } catch (e) {
        o.viewCountText = ""
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

        newHeader.imgSrc = r.thumbnail.thumbnails[r.thumbnail.thumbnails.length - 1].url
    } catch (e) {
    }
    try {

        newHeader.channelId = r.navigationEndpoint.browseEndpoint.browseId
    } catch (e) {

    }

    o.channelInfo = newHeader;
}



function getList() {
    var arr = [];

    for (itemKey in items) {
        var o = {};
        var item = items[itemKey].videoRenderer;
        if (item) {
            extractVideo(o, item);
            arr.push(o);
        } else {
            item = items[itemKey].playlistRenderer;
            if (item) {
                extractPlayList(o, item);
                arr.push(o);
            }
        }

        //shelfRenderer

    }
    return JSON.stringify(arr)
}
