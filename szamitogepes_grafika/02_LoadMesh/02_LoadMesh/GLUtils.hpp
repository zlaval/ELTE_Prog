#include <stdio.h>
#include <string>
#include <iostream>
#include <fstream>
#include <vector>

#include <GL/glew.h>

#include <SDL_image.h>

/* 

Az http://www.opengl-tutorial.org/ oldal alapján.

*/
GLuint loadShader(GLenum _shaderType, const char* _fileName)
{
	// shader azonosito letrehozasa
	GLuint loadedShader = glCreateShader( _shaderType );

	// ha nem sikerult hibauzenet es -1 visszaadasa
	if ( loadedShader == 0 )
	{
		fprintf(stderr, "Hiba a %s shader inicializálásakor (glCreateShader)!", _fileName);
		return 0;
	}
	
	// shaderkod betoltese _fileName fajlbol
	std::string shaderCode = "";

	// _fileName megnyitasa
	std::ifstream shaderStream(_fileName);

	if ( !shaderStream.is_open() )
	{
		fprintf(stderr, "Hiba a %s shader fájl betöltésekor!", _fileName);
		return 0;
	}

	// file tartalmanak betoltese a shaderCode string-be
	std::string line = "";
	while ( std::getline(shaderStream, line) )
	{
		shaderCode += line + "\n";
	}

	shaderStream.close();

	// fajlbol betoltott kod hozzarendelese a shader-hez
	const char* sourcePointer = shaderCode.c_str();
	glShaderSource( loadedShader, 1, &sourcePointer, NULL );

	// shader leforditasa
	glCompileShader( loadedShader );

	// ellenorizzuk, h minden rendben van-e
	GLint result = GL_FALSE;
    int infoLogLength;

	// forditas statuszanak lekerdezese
	glGetShaderiv(loadedShader, GL_COMPILE_STATUS, &result);
	glGetShaderiv(loadedShader, GL_INFO_LOG_LENGTH, &infoLogLength);

	if ( GL_FALSE == result )
	{
		// hibauzenet elkerese es kiirasa
		std::vector<char> VertexShaderErrorMessage(infoLogLength);
		glGetShaderInfoLog(loadedShader, infoLogLength, NULL, &VertexShaderErrorMessage[0]);

		fprintf(stdout, "%s\n", &VertexShaderErrorMessage[0]);
	}

	return loadedShader;
}

GLuint loadProgramVSGSFS(const char* _fileNameVS, const char* _fileNameGS, const char* _fileNameFS)
{
	// a vertex, geometry es fragment shaderek betoltese
	GLuint vs_ID = loadShader(GL_VERTEX_SHADER,		_fileNameVS);
	GLuint gs_ID = loadShader(GL_GEOMETRY_SHADER,	_fileNameGS);
	GLuint fs_ID = loadShader(GL_FRAGMENT_SHADER,	_fileNameFS);

	// ha barmelyikkel gond volt programot sem tudunk csinalni, 0 vissza
	if ( vs_ID == 0 || gs_ID == 0 || fs_ID == 0 )
	{
		return 0;
	}

	// linkeljuk ossze a dolgokat
	GLuint program_ID = glCreateProgram();

	fprintf(stdout, "Linking program\n");
	glAttachShader(program_ID, vs_ID);
	glAttachShader(program_ID, gs_ID);
	glAttachShader(program_ID, fs_ID);

	glLinkProgram(program_ID);

	// linkeles ellenorzese
	GLint infoLogLength = 0, result = 0;

	glGetProgramiv(program_ID, GL_LINK_STATUS, &result);
	glGetProgramiv(program_ID, GL_INFO_LOG_LENGTH, &infoLogLength);
	if ( GL_FALSE == result)
	{
		std::vector<char> ProgramErrorMessage( infoLogLength );
		glGetProgramInfoLog(program_ID, infoLogLength, NULL, &ProgramErrorMessage[0]);
		fprintf(stdout, "%s\n", &ProgramErrorMessage[0]);
	}

	// mar nincs ezekre szukseg
	glDeleteShader( vs_ID );
	glDeleteShader( gs_ID );
	glDeleteShader( fs_ID );

	// adjuk vissza a program azonositojat
	return program_ID;
}

GLuint TextureFromFile(const char* filename)
{
	SDL_Surface* loaded_img = IMG_Load(filename);  

	int img_mode = 0;
	
	if ( loaded_img == 0 )
	{
		std::cout << "[TextureFromFile] Hiba a kép betöltése közben: " << filename << std::endl;
		return 0;
	}

	#if SDL_BYTEORDER == SDL_LIL_ENDIAN
		if ( loaded_img->format->BytesPerPixel == 4 )
			img_mode = GL_BGRA;
		else
			img_mode = GL_BGR;
	#else
		if ( loaded_img->format->BytesPerPixel == 4 )
			img_mode = GL_RGBA;
		else
			img_mode = GL_RGB;
	#endif

    GLuint tex;
    glGenTextures(1, &tex);
  
	glBindTexture(GL_TEXTURE_2D, tex);
	glTexImage2D(GL_TEXTURE_2D,			// melyik binding point-on van a textúra erõforrás, amihez tárolást rendelünk
		0,								// melyik részletességi szint adatait határozzuk meg
		GL_RGB,							// textúra belsõ tárolási formátuma (GPU-n)
		loaded_img->w, loaded_img->h,	// szélesség, magasság
		0,								// nulla kell, hogy legyen ( https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/glTexImage2D.xhtml )
		img_mode,						// forrás (=CPU-n) formátuma
		GL_UNSIGNED_BYTE,				// forrás egy pixelének egy csatornáját hogyan tároljuk
		loaded_img->pixels);			// forráshoz pointer
	glGenerateMipmap(GL_TEXTURE_2D);
    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
  
	SDL_FreeSurface( loaded_img );

    return tex;
}

GLuint genSampler(GLint param_min = GL_LINEAR, GLint param_mag = GL_LINEAR_MIPMAP_LINEAR)
{
	GLuint samp;
	glGenSamplers(1, &samp);

	glSamplerParameteri(samp, GL_TEXTURE_MIN_FILTER, param_min);
	glSamplerParameteri(samp, GL_TEXTURE_MAG_FILTER, param_mag);

	return samp;
}