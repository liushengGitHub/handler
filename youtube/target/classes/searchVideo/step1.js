var window = {};
var context = {};
var ytcfg = {
    set: function (o) {
        if (o.INNERTUBE_CONTEXT) {
            context = o.INNERTUBE_CONTEXT;
        }
    }
}

function extractPlayList(o, item) {
    o.playlistId = item.playlistId;
    try {
        o.name = item.title.simpleText
    } catch (e) {
        o.name = ""
    }
    try {
        o.videoId = item.videos[0].childVideoRenderer.videoId
    } catch (e) {
        o.videoId = ""
    }
    try {
        o.name = item.title.simpleText
    } catch (e) {
        o.name = ""
    }
    try {
        o.publishedTimeText = item.title.simpleText
    } catch (e) {
        o.publishedTimeText = ""
    }
    try {
        o.viewCountText = item.videoCount
    } catch (e) {
        o.viewCountText = ""
    }

    try {

        o.imgSrc = item.thumbnailRenderer.playlistVideoThumbnailRenderer.thumbnail.thumbnails[0].url
    } catch (e) {
        o.imgSrc = ""
    }
}
