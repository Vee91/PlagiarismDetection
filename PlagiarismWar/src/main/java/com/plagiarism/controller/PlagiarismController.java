package com.plagiarism.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.plagiarism.response.Response;
import com.plagiarism.service.AlgorithmBase;

@Controller
public class PlagiarismController {

	private AlgorithmBase algorithm = new AlgorithmBase();

	@RequestMapping(value = "/compareFiles", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Response welcome(@RequestParam(value = "file0", required = false) List<MultipartFile> file0,
			@RequestParam(value = "file1", required = false) List<MultipartFile> file1) throws IOException {
		Response r = new Response();
		if ((file0 != null && file0 != null) && file0.size() != 0 && file1.size() != 0 ) {
			return algorithm.run(file0.get(0), file1.get(0));
			/*r.setCode(200);
			r.setMessage(String.valueOf(Math.round(result * 10000.0) / 100.0) + "%");*/
		} else {
			r.setCode(402);
			r.setError("There was an error in uploading files. Please try again");
		}

		return r;
	}

}
