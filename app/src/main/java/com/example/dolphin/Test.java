package com.example.dolphin;


import java.io.IOException;

/**
 * @author 王景阳
 * @date 2022/10/29 16:28
 */
public class Test {
    public static void main(String[] args) throws IOException {
       /* VideoApi videoApi = RetrofitUtils.create(VideoApi.class);
        File cover = new File("C:\\Users\\d\\Desktop\\视频\\concern.png");
        RequestBody coverBody = RequestBody.create(cover, MediaType.parse("multipart/form-data"));
        MultipartBody.Part coverPart = MultipartBody.Part.createFormData("cover", cover.getName(), coverBody);
        File video = new File("C:\\Users\\d\\Desktop\\视频\\1.mp4");
        FileInputStream videoInputStream = new FileInputStream(video);
        int length = 3 * 1024 * 1024;
        byte[] bytes = new byte[length];
        int len = 0;
        boolean first = true;
        VideoOutput videoOutput = new VideoOutput();
        videoOutput.setVideoName(video.getName())
                .setCoverName(cover.getName())
                .setUserName("wjy")
                .setVideoIntroduction("wjy------")
                .setHasCover(false)
                .setEnd(true);
        while ((len = videoInputStream.read(bytes)) > 0) {
            System.out.println(len);
            RequestBody videoBody = RequestBody.create(Arrays.copyOfRange(bytes, 0, len), MediaType.parse("multipart/form-data"));
            MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", video.getName(), videoBody);
            videoOutput.setEnd(len != length);
            Call<Result<Boolean>> resultCall = videoApi.uploadVideo(JSON.toJSONString(videoOutput), videoPart, first ? coverPart : null);
            Result<Boolean> booleanResult = ApiTool.sendRequest(resultCall);
            System.out.println(booleanResult);
            first = false;
        }*/
    }
}