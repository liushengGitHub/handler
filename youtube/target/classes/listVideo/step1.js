var window = {};

function getList0(items,header) {

    var arr = []
    for (itemKey in items) {
        var o = {};
        var item = items[itemKey].playlistPanelVideoRenderer;
        o.channelInfo = header;
        o.videoId = item.videoId;
        try {

            o.name = item.title.simpleText
        } catch (e) {
            o.name = ""
        }
        try {

            o.imgSrc = item.thumbnail.thumbnails[item.thumbnail.thumbnails.length-1].url;
        } catch (e) {
            o.imgSrc = ""
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
        arr.push(o);
    }
    return JSON.stringify(arr)
}
