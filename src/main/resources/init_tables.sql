CREATE SCHEMA docs AUTHORIZATION postgres;

CREATE TABLE docs.document_reg (
   id bigserial NOT NULL,
   id_doc int8 NULL,
   date_reg timestamp NULL
);
CREATE TABLE docs.documents (
    id bigserial NOT NULL,
    series varchar NULL,
    "number" varchar NULL,
    "name" varchar NULL,
    code_type varchar NOT NULL
);

CREATE TABLE public.info (
     id serial4 NOT NULL,
     "data" text NOT NULL,
     CONSTRAINT info_pkey PRIMARY KEY (id)
);
CREATE TABLE public.docs (
     id serial4 NOT NULL,
     "number" varchar NULL,
     "name" varchar NULL,
     info_id int4 NULL,
     CONSTRAINT fk_docs_info FOREIGN KEY (info_id) REFERENCES public.info(id)
);