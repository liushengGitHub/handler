var items = window["ytInitialData"].contents
    .twoColumnWatchNextResults
    .playlist
    .playlist
    .contents


function getList() {
    return getList0(items)
}
function extractVideo(o,item) {
    o.videoId = item.videoId;
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

