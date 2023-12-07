package org.bedu.spotify.service;

import java.util.List;
import java.util.Optional;

import org.bedu.spotify.dto.ArtistDTO;
import org.bedu.spotify.dto.CreateArtistDTO;
import org.bedu.spotify.dto.UpdateArtistDTO;
import org.bedu.spotify.exception.ArtistNotFoundException;
import org.bedu.spotify.mapper.ArtistMapper;
import org.bedu.spotify.model.Artist;
import org.bedu.spotify.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    
    @Autowired
    private ArtistRepository repository;

    @Autowired
    private ArtistMapper mapper;

    public List<ArtistDTO> findAll() {
        return mapper.toDTO(repository.findAll());
    }

    public ArtistDTO save(CreateArtistDTO data) {
        Artist entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    public void update(long artistId, UpdateArtistDTO data) throws ArtistNotFoundException {
        Optional<Artist> result = repository.findById(artistId);

        if (!result.isPresent()) {
            throw new ArtistNotFoundException(artistId);
        }

        Artist artist = result.get();

        // Aplicar los cambios al artista
        mapper.update(artist, data);

        repository.save(artist);
    }
} 
