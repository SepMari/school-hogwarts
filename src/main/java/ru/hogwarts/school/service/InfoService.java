package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoService {

    @Value("${server.port}")
    private int port;
    Logger logger = LoggerFactory.getLogger(InfoService.class);

    public int getPort() {
        logger.info("Was invoked method for get port");
        return port;
    }

    public int getSumMillion() {
        return  Stream.iterate(1, a -> a + 1)
//                .parallel()
                .limit(1_000_000)
                .mapToInt(Integer::intValue).sum(); // 136 ms без parallel, с ней 164 ms
//                .reduce(0, Integer::sum); // 148 ms без parallel, 139 ms с ней
    }

}
