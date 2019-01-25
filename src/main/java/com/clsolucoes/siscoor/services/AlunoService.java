package com.clsolucoes.siscoor.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//import com.clsolucoes.siscoor.domain.Cidade;
import com.clsolucoes.siscoor.domain.Aluno;
//import com.clsolucoes.siscoor.domain.Endereco;
import com.clsolucoes.siscoor.domain.enums.Perfil;
import com.clsolucoes.siscoor.dto.AlunoDTO;
import com.clsolucoes.siscoor.dto.AlunoNewDTO;
import com.clsolucoes.siscoor.repositories.AlunoRepository;
//import com.clsolucoes.siscoor.repositories.EnderecoRepository;
import com.clsolucoes.siscoor.security.UserSS;
import com.clsolucoes.siscoor.services.exceptions.AuthorizationException;
import com.clsolucoes.siscoor.services.exceptions.DataIntegrityException;
import com.clsolucoes.siscoor.services.exceptions.ObjectNotFoundException;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository repo;
	
	//@Autowired
	//private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
		
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Aluno find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Aluno> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Aluno.class.getName()));
	}
	
	@Transactional
	public Aluno insert(Aluno obj) {
		obj.setId(null);
		obj = repo.save(obj);
		//enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Aluno update(Aluno obj) {
		Aluno newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há grupos relacionados");
		}
	}
	
	public List<Aluno> findAll() {
		return repo.findAll();
	}
	
	public Aluno findByEmailAluno(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Aluno obj = repo.findByEmailAluno(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Aluno.class.getName());
		}
		return obj;
	}
	
	public Page<Aluno> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Aluno fromDTO(AlunoDTO objDto) {
		return new Aluno(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Aluno fromDTO(AlunoNewDTO objDto) {
		Aluno alu = new Aluno(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfAluno(), pe.encode(objDto.getSenha()));
		//Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		//Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		//cli.getEnderecos().add(end);
		alu.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			alu.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			alu.getTelefones().add(objDto.getTelefone3());
		}
		return alu;
	}
	
	private void updateData(Aluno newObj, Aluno obj) {
		newObj.setNomeAluno(obj.getNomeAluno());
		newObj.setEmailAluno(obj.getEmailAluno());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
