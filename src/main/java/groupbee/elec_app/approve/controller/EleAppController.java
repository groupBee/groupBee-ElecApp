package groupbee.elec_app.approve.controller;


import groupbee.elec_app.approve.data.ElecApp;
import groupbee.elec_app.approve.service.ElecAppService;
import groupbee.elec_app.service.minio.MinioService;
import groupbee.elec_app.service.minio.ObjectStorage;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class EleAppController {

    final private ElecAppService elecAppService;
    final private MinioService minioService;

    @PostMapping("/elecapp/create")
    public void saveEleApp(@RequestBody ElecApp elecApp){
        elecAppService.save(elecApp);
    }

    @PostMapping("/elecapp/uploadfile")
    private String uploadFile(@RequestParam("file") MultipartFile file){

        String fileName=minioService.uploadFile("elec_app","groupbee",file);

        return fileName;
    }

}
