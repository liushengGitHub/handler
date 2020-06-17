var items = window["ytInitialPlayerResponse"].captions
    .playerCaptionsTracklistRenderer
    .captionTracks


function getList() {
    var arr = []
    for (itemKey in items) {
        var o = {};
        var item = items[itemKey];
        extractVideo(o, item);
        arr.push(o);
    }
    return JSON.stringify(arr)
}

function extractVideo(o, item) {
    o.baseUrl = item.baseUrl;
    try {
        o.name = item.name.simpleText;

    } catch (e) {
        o.name = "";
    }
    o.vssId = item.vssId;
    o.isTranslatable = item.isTranslatable;
}

