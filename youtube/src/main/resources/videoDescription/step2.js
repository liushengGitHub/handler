var responseStr = ytplayer.config
    .args
    .player_response

function handle(response) {
    var  videoDetails = response.videoDetails;
    try{
        var data = null;
        var  elements = response.endscreen.endscreenRenderer.elements;
        for (e in elements){
            if ( elements[e].endscreenElementRenderer.style == 'CHANNEL') {
                    data = elements[e].endscreenElementRenderer;
            }
        }
        videoDetails.avatorUrl =  data.image.thumbnails[data.image.thumbnails.length -1].url
    }catch (e) {

    }

    try{
        var  publishDate
            = response.microformat.playerMicroformatRenderer.publishDate

        videoDetails.publishDate = publishDate
    }catch (e) {

    }
}

function getList() {
    var response = JSON.parse(responseStr);
    handle(response);
    var  videoDetails = response.videoDetails;
    return JSON.stringify(videoDetails);
}

