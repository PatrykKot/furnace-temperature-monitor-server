package com.kotlarz.web.api.database.rest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping( "database" )
public class DatabaseCopyController
{
    @Autowired
    private JdbcTemplate template;

    private byte[] getDatabase()
                    throws IOException
    {
        String tempDir = System.getProperty( "java.io.tmpdir" );
        String zipPath = Paths.get( tempDir )
                        .resolve( "furnace_temperature_monitor_server" )
                        .resolve( UUID.randomUUID() + ".zip" )
                        .toString();
        template.execute( "BACKUP TO '" + zipPath + "'" );

        byte[] bytes;
        try (InputStream stream = new FileInputStream( zipPath ))
        {
            bytes = IOUtils.toByteArray( stream );
        }

        Files.delete( Paths.get( zipPath ) );

        return bytes;
    }

    @GetMapping
    public HttpEntity<byte[]> downloadDatabase()
                    throws IOException
    {
        byte[] database = getDatabase();

        HttpHeaders header = new HttpHeaders();
        header.setContentType( MediaType.APPLICATION_OCTET_STREAM );
        header.set( HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=db_backup_" + new Date().getTime() + ".zip" );
        header.setContentLength( database.length );

        return new HttpEntity<>( database, header );
    }
}
