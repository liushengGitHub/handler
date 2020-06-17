function nextFun() {
    try {
        var continuation = arr[1].response.continuationContents
            .gridContinuation;
        var next = continuation.continuations[0].nextContinuationData;
        return JSON.stringify(next);
    } catch (e) {
        return ""
    }

}

function getList() {

    var arr1 = []
    try {
        var continuation = arr[1].response.continuationContents
            .gridContinuation;
        var items = continuation.items;
        var header = arr[1].response.metadata.channelMetadataRenderer
        var newHeader = {};
        newHeader.name = header.title
        try {
            newHeader.imgSrc = header.avatar.thumbnails[header.avatar.thumbnails.length - 1].url
        } catch (e) {
        }
        newHeader.channelUrl = header.channelUrl
        return getList0(items,newHeader)
    } catch (e) {
    }

    return JSON.stringify(arr1)
}
