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
#include <vector>

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
	void CMyApp::SpacePress();
	void Resize(int, int);
protected:
	
	GLuint m_programID = 0;

	GLuint m_vaoID = 0; 
	GLuint m_vboID = 0; 

	glm::mat4 m_matWorld = glm::mat4(1);
	glm::mat4 m_matView = glm::mat4(1);
	glm::mat4 m_matProj = glm::mat4(1);

	// mátrixok helye a shaderekben
	GLuint	m_loc_world = 0;
	GLuint	m_loc_view = 0;
	GLuint	m_loc_proj = 0;

	int numberOfShapes = 1;
	bool spacePressed = 0;
	std::vector<glm::vec3> shift;

		struct Vertex
	{
		glm::vec3 p;
		glm::vec3 c;
	};
};

