package com.ka.udemyspringboothackingcourse.secure.services;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service("DNSLookupServiceV2")
@AllArgsConstructor
@CommonsLog
public class DNSLookupService {

    public String getDNS(String domain) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("cmd.exe", "/c", "nslookup " + domain);
        builder.directory(new File(System.getProperty("user.home")));
        Process process = builder.start();

        List<String> output = readOutput(process.getInputStream());
        return formatOutput(output);

    }

    private List<String> readOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

    private String formatOutput(List<String> output) {
        StringBuilder sb = new StringBuilder();
        for (String out: output) {
            sb.append("\n");
            sb.append(out);
        }
        return sb.toString();
    }
}
