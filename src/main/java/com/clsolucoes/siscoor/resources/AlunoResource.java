package com.clsolucoes.siscoor.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.clsolucoes.siscoor.domain.Aluno;
import com.clsolucoes.siscoor.dto.AlunoDTO;
import com.clsolucoes.siscoor.dto.AlunoNewDTO;
import com.clsolucoes.siscoor.services.AlunoService;

@RestController
@RequestMapping(value="/alunos")
public class AlunoResource {
		
		@Autowired
		private AlunoService alunoService;
		
		@RequestMapping(value="/{id}", method=RequestMethod.GET)
		public ResponseEntity<Aluno> find(@PathVariable Integer id) {
			Aluno obj = alunoService.find(id);
			return ResponseEntity.ok().body(obj);
		}
		
		@RequestMapping(value="/email", method=RequestMethod.GET)
		public ResponseEntity<Aluno> find(@RequestParam(value="value") String email) {
			Aluno obj = alunoService.findByEmailAluno(email);
			return ResponseEntity.ok().body(obj);
		}
		
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Void> insert(@Valid @RequestBody AlunoNewDTO objDto) {
			Aluno obj = alunoService.fromDTO(objDto);
			obj = alunoService.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}
		
		@RequestMapping(value="/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Void> update(@Valid @RequestBody AlunoDTO objDto, @PathVariable Integer id) {
			Aluno obj = alunoService.fromDTO(objDto);
			obj.setId(id);
			obj = alunoService.update(obj);
			return ResponseEntity.noContent().build();
		}
		
		@PreAuthorize("hasAnyRole('ADMIN')")
		@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<Void> delete(@PathVariable Integer id) {
			alunoService.delete(id);
			return ResponseEntity.noContent().build();
		}
		
		
		@RequestMapping(method=RequestMethod.GET)
		public ResponseEntity<List<AlunoDTO>> findAll() {
			List<Aluno> list = alunoService.findAll();
			List<AlunoDTO> listDto = list.stream().map(obj -> new AlunoDTO(obj)).collect(Collectors.toList());  
			return ResponseEntity.ok().body(listDto);
		}
		
		
		@RequestMapping(value="/page", method=RequestMethod.GET)
		public ResponseEntity<Page<AlunoDTO>> findPage(
				@RequestParam(value="page", defaultValue="0") Integer page, 
				@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
				@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
				@RequestParam(value="direction", defaultValue="ASC") String direction) {
			Page<Aluno> list = alunoService.findPage(page, linesPerPage, orderBy, direction);
			Page<AlunoDTO> listDto = list.map(obj -> new AlunoDTO(obj));  
			return ResponseEntity.ok().body(listDto);
		}	
		
		@RequestMapping(value="/picture", method=RequestMethod.POST)
		public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
			URI uri = alunoService.uploadProfilePicture(file);
			return ResponseEntity.created(uri).build();
		}
}
