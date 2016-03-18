--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: albumcontains; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE albumcontains (
    album_id integer,
    picture_id integer
);


ALTER TABLE public.albumcontains OWNER TO yanjxx;

--
-- Name: albums_album_id_seq; Type: SEQUENCE; Schema: public; Owner: yanjxx
--

CREATE SEQUENCE albums_album_id_seq
    START WITH 14
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.albums_album_id_seq OWNER TO yanjxx;

--
-- Name: albums; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE albums (
    album_id integer DEFAULT nextval('albums_album_id_seq'::regclass) NOT NULL,
    name character varying(50) NOT NULL,
    owner_id integer NOT NULL,
    date_of_creation date NOT NULL
);


ALTER TABLE public.albums OWNER TO yanjxx;

--
-- Name: comments_comment_id_seq; Type: SEQUENCE; Schema: public; Owner: yanjxx
--

CREATE SEQUENCE comments_comment_id_seq
    START WITH 14
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comments_comment_id_seq OWNER TO yanjxx;

--
-- Name: comments; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE comments (
    comment_id integer DEFAULT nextval('comments_comment_id_seq'::regclass) NOT NULL,
    user_id integer NOT NULL,
    picture_id integer,
    text character varying(255),
    date_of_comment date NOT NULL
);


ALTER TABLE public.comments OWNER TO yanjxx;

--
-- Name: friends; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE friends (
    add_id integer NOT NULL,
    added_id integer NOT NULL,
    CONSTRAINT noselfriend CHECK ((add_id <> added_id))
);


ALTER TABLE public.friends OWNER TO yanjxx;

--
-- Name: likepicture; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE likepicture (
    user_id integer,
    picture_id integer
);


ALTER TABLE public.likepicture OWNER TO yanjxx;

--
-- Name: pictures_picture_id_seq; Type: SEQUENCE; Schema: public; Owner: yanjxx
--

CREATE SEQUENCE pictures_picture_id_seq
    START WITH 14
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pictures_picture_id_seq OWNER TO yanjxx;

--
-- Name: pictures; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE pictures (
    picture_id integer DEFAULT nextval('pictures_picture_id_seq'::regclass) NOT NULL,
    caption character varying(255) NOT NULL,
    imgdata bytea NOT NULL,
    size integer NOT NULL,
    content_type character varying(255) NOT NULL,
    thumbdata bytea NOT NULL,
    album_id integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.pictures OWNER TO yanjxx;

--
-- Name: stations; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE stations (
    id integer,
    terminal character(6),
    station character varying(120),
    municipal character varying(20),
    lat numeric(8,6),
    lng numeric(8,6),
    status character varying(20)
);


ALTER TABLE public.stations OWNER TO yanjxx;

--
-- Name: tags_tag_id_seq; Type: SEQUENCE; Schema: public; Owner: yanjxx
--

CREATE SEQUENCE tags_tag_id_seq
    START WITH 14
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tags_tag_id_seq OWNER TO yanjxx;

--
-- Name: tags; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE tags (
    tag_id integer DEFAULT nextval('tags_tag_id_seq'::regclass) NOT NULL,
    tag character varying(60),
    picture_id integer NOT NULL
);


ALTER TABLE public.tags OWNER TO yanjxx;

--
-- Name: trips; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE trips (
    seq_id integer,
    hubway_id integer,
    status character varying(120),
    duration integer,
    strat_date timestamp without time zone,
    strt_statn integer,
    end_date timestamp without time zone,
    end_statn integer,
    bike_nr character(6),
    subsc_type character varying(120),
    zip_code character varying(120),
    birth_date integer,
    gender character varying(20)
);


ALTER TABLE public.trips OWNER TO yanjxx;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: yanjxx
--

CREATE SEQUENCE users_user_id_seq
    START WITH 14
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO yanjxx;

--
-- Name: users; Type: TABLE; Schema: public; Owner: yanjxx; Tablespace: 
--

CREATE TABLE users (
    user_id integer DEFAULT nextval('users_user_id_seq'::regclass) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role_name character varying(255) DEFAULT 'RegisteredUser'::character varying NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    date_of_birth character varying(12) NOT NULL,
    hometown character varying(255) NOT NULL,
    gender character varying(20) NOT NULL
);


ALTER TABLE public.users OWNER TO yanjxx;

--
-- Name: albums_pkey; Type: CONSTRAINT; Schema: public; Owner: yanjxx; Tablespace: 
--

ALTER TABLE ONLY albums
    ADD CONSTRAINT albums_pkey PRIMARY KEY (album_id);


--
-- Name: comment_id_pk; Type: CONSTRAINT; Schema: public; Owner: yanjxx; Tablespace: 
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT comment_id_pk PRIMARY KEY (comment_id);


--
-- Name: pictures_pk; Type: CONSTRAINT; Schema: public; Owner: yanjxx; Tablespace: 
--

ALTER TABLE ONLY pictures
    ADD CONSTRAINT pictures_pk PRIMARY KEY (picture_id);


--
-- Name: tag_pk; Type: CONSTRAINT; Schema: public; Owner: yanjxx; Tablespace: 
--

ALTER TABLE ONLY tags
    ADD CONSTRAINT tag_pk PRIMARY KEY (tag_id);


--
-- Name: users_pk; Type: CONSTRAINT; Schema: public; Owner: yanjxx; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);


--
-- Name: add_fk; Type: FK CONSTRAINT; Schema: public; Owner: yanjxx
--

ALTER TABLE ONLY friends
    ADD CONSTRAINT add_fk FOREIGN KEY (add_id) REFERENCES users(user_id);


--
-- Name: added_fk; Type: FK CONSTRAINT; Schema: public; Owner: yanjxx
--

ALTER TABLE ONLY friends
    ADD CONSTRAINT added_fk FOREIGN KEY (added_id) REFERENCES users(user_id);


--
-- Name: albumcontains_album_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yanjxx
--

ALTER TABLE ONLY albumcontains
    ADD CONSTRAINT albumcontains_album_id_fkey FOREIGN KEY (album_id) REFERENCES albums(album_id);


--
-- Name: albumcontains_picture_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: yanjxx
--

ALTER TABLE ONLY albumcontains
    ADD CONSTRAINT albumcontains_picture_id_fkey FOREIGN KEY (picture_id) REFERENCES pictures(picture_id);


--
-- Name: pic_fk; Type: FK CONSTRAINT; Schema: public; Owner: yanjxx
--

ALTER TABLE ONLY tags
    ADD CONSTRAINT pic_fk FOREIGN KEY (picture_id) REFERENCES pictures(picture_id);


--
-- Name: pic_fk; Type: FK CONSTRAINT; Schema: public; Owner: yanjxx
--

ALTER TABLE ONLY likepicture
    ADD CONSTRAINT pic_fk FOREIGN KEY (picture_id) REFERENCES pictures(picture_id);


--
-- Name: picture_fk; Type: FK CONSTRAINT; Schema: public; Owner: yanjxx
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT picture_fk FOREIGN KEY (picture_id) REFERENCES pictures(picture_id);


--
-- Name: to_owner; Type: FK CONSTRAINT; Schema: public; Owner: yanjxx
--

ALTER TABLE ONLY albums
    ADD CONSTRAINT to_owner FOREIGN KEY (owner_id) REFERENCES users(user_id);


--
-- Name: user_fk; Type: FK CONSTRAINT; Schema: public; Owner: yanjxx
--

ALTER TABLE ONLY likepicture
    ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

