CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	login VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	usuario_id BIGINT(20) NOT NULL,
	permissao_id BIGINT(20) NOT NULL,
	PRIMARY KEY (usuario_id, permissao_id),
	FOREIGN KEY (usuario_id) REFERENCES usuario(id),
	FOREIGN KEY (permissao_id) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario (nome, login, email, senha) values ('Administrador', 'admin' , 'admin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario (nome, login, email, senha) values ('Kevin Max', 'kevin.max' ,'kevin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');

INSERT INTO permissao (nome) values ('ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao (nome) values ('ROLE_PESQUISAR_CATEGORIA');
INSERT INTO permissao (nome) values ('ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao (nome) values ('ROLE_REMOVER_PESSOA');
INSERT INTO permissao (nome) values ('ROLE_PESQUISAR_PESSOA');
INSERT INTO permissao (nome) values ('ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao (nome) values ('ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao (nome) values ('ROLE_PESQUISAR_LANCAMENTO');

-- admin
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 1);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 2);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 3);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 4);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 5);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 6);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 7);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (1, 8);

-- kevin
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (2, 2);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (2, 5);
INSERT INTO usuario_permissao (usuario_id, permissao_id) values (2, 8);