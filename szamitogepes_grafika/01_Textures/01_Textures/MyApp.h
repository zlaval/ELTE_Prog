#pragma once

// GLEW
#include <GL/glew.h>

// SDL
#include <SDL.h>
#include <SDL_opengl.h>

// GLM
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtx/transform2.hpp>

class CMyApp
{
public:
	CMyApp();
	~CMyApp();

	bool Init();
	void Clean();

	void Update();
	void Render();

	void KeyboardDown(SDL_KeyboardEvent&);
	void KeyboardUp(SDL_KeyboardEvent&);
	void MouseMove(SDL_MouseMotionEvent&);
	void MouseDown(SDL_MouseButtonEvent&);
	void MouseUp(SDL_MouseButtonEvent&);
	void MouseWheel(SDL_MouseWheelEvent&);
	void Resize(int, int);
	

protected:
	struct Vertex
	{
		glm::vec3 p;
		glm::vec2 t;
	};

	// belső eljárások
	GLuint GenerateRandomTexture();
	glm::vec3 toDescatres(float fi, float theta);

	// shaderekhez szükséges változók
	GLuint m_programID = 0; // shaderprogram erőforrás azonosító

	// OpenGL-es dolgok
	GLuint m_floor_vaoID = 0; // vertex array object erőforrás azonosító
	GLuint m_floor_vboID = 0; // vertex buffer object erőforrás azonosító
	GLuint m_floor_ibID = 0;  // index buffer object erőforrás azonosító
	GLuint m_cube_vaoID = 0;
	GLuint m_cube_vboID = 0;
	GLuint m_cube_ibID = 0;
	GLuint m_generatedTextureID = 0; // generált textúra erőforrás azonosító
	GLuint m_loadedTextureID = 0; // betöltött textúra erőforrás azonosító

	// transzformációs mátrixok
	glm::mat4 m_matWorld = glm::mat4(1.0f);
	glm::mat4 m_matView = glm::mat4(1.0f);
	glm::mat4 m_matProj = glm::mat4(1.0f);

	// uniform változók helye a shaderekben
	GLuint	m_loc_mvp = 0;
	GLuint  m_loc_w = 0;
	GLuint	m_loc_tex = 0;
	GLuint	m_loc_tex2 = 0;

	float fi = M_PI * 1.5f;
	float theta = M_PI / 2.f;

	glm::vec3 eye = glm::vec3(0, 0, 10);
	glm::vec3 forward = toDescatres(fi,theta);
	glm::vec3 look_at = eye + forward;
	glm::vec3 up=glm::vec3(0,1,0);
	glm::vec3 right = glm::cross(up, forward);

	bool is_left_pressed=false;

	// a jobb olvashatóság kedvéért
	void InitCube();
	void InitFloor();
	void InitShaders();
	void InitTextures();
};

