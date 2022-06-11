#pragma once

// C++ includes
#include <memory>

// GLEW
#include <GL/glew.h>

// SDL
#include <SDL.h>
#include <SDL_opengl.h>

// GLM
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtx/transform2.hpp>

#include "includes/gCamera.h"

#include "includes/ProgramObject.h"
#include "includes/BufferObject.h"
#include "includes/VertexArrayObject.h"
#include "includes/TextureObject.h"

#include "includes/ObjParser_OGL3.h"

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

	ProgramObject		m_program;			
	ProgramObject		suz_shader;
	ProgramObject		m_programSkybox;

	VertexArrayObject	m_SkyboxVao;
	IndexBuffer			m_SkyboxIndices;	
	ArrayBuffer			m_SkyboxPos;		

	gCamera				m_camera;

	Texture2D			m_woodTexture;
	Texture2D			m_suzanneTexture;
	Texture2D           cowTexture;
	Texture2D           ironTexture;
	TextureCubeMap		m_skyboxTexture;

	struct Vertex
	{
		glm::vec3 p;
		glm::vec3 n;
		glm::vec2 t;
	};

	float uvVal =1.5;
	float dolly_focus = 30;
	float dolly_fov = 60;
	float prev_dolly_focus = 30;
	float prev_dolly_fov = 60;
	bool changed_dolly_focus = false;
	bool changed_dolly_fov = false;
	bool dolly_zoom_enabled = false;
	
	std::unique_ptr<Mesh> m_mesh;

	std::unique_ptr<Mesh> cow_mesh;

	void InitShaders();
	void InitSkyBox();
};

