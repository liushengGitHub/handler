var gridRenderer = window["ytInitialData"].contents.twoColumnBrowseResultsRenderer.tabs[1].tabRenderer.content.sectionListRenderer.contents[0].itemSectionRenderer.contents[0].gridRenderer;
var items = gridRenderer
    .items;

var header = window["ytInitialData"].header.c4TabbedHeaderRenderer


function nextFun() {
    try {
        var next = gridRenderer.continuations[0].nextContinuationData
        return JSON.stringify(next);
    } catch (e) {
        return ""
    }

}

function getList() {
    var newHeader = {};
    newHeader.name = header.title
    try {

        newHeader.imgSrc = header.avatar.thumbnails[header.avatar.thumbnails.length -1].url
    } catch (e) {
    }
    newHeader.channelId = header.channelId
    return getList0(items, newHeader)
}

