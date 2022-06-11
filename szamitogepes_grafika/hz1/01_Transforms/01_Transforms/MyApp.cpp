#include "MyApp.h"
#include "GLUtils.hpp"

#include <math.h>

CMyApp::CMyApp()
{
	srand((unsigned int)time(NULL));
	numberOfShapes = (rand() % 7) + 4;
	std::cout << "Number of shapes: " << numberOfShapes << std::endl;

	for (int i = 0; i < numberOfShapes; ++i) {
		float x = static_cast <float> (rand()) / (static_cast <float> (RAND_MAX / 5.f)) - 2.5f;
		float y = static_cast <float> (rand()) / (static_cast <float> (RAND_MAX / 4.f));
		float z = static_cast <float> (rand()) / (static_cast <float> (RAND_MAX / 5.f)) - 2.5f;

		std::cout << (i + 1) << ". shape coordinates: (" << x << "," << y << "," << z << ")" << std::endl;

		shift.push_back(glm::vec3(x, y, z));
	}

}

CMyApp::~CMyApp()
{
}

bool CMyApp::Init()
{
	//std::vector<glm::vec3> shift;



	glClearColor(0.125f, 0.25f, 0.5f, 1.0f);

	glPolygonMode(GL_BACK, GL_LINE);
	glEnable(GL_DEPTH_TEST);
	glCullFace(GL_BACK);


	Vertex vert[] =
	{

		//base
		{glm::vec3(-0.5, 0, -0.5), glm::vec3(1, 0, 0)},
		{glm::vec3(0.5, 0, -0.5), glm::vec3(1, 0, 0)},
		{glm::vec3(-0.5, 0,  0.5), glm::vec3(1, 0, 1)},

		{glm::vec3(0.5, 0, -0.5), glm::vec3(0, 1, 0)},
		{glm::vec3(0.5, 0,  0.5), glm::vec3(0, 1, 0)},
		{glm::vec3(-0.5, 0,  0.5), glm::vec3(0, 1, 0)},

		//back
		{glm::vec3(-0.5, 0, -0.5), glm::vec3(0, 0, 1)},
		{glm::vec3(-0.5, 1, -0.5), glm::vec3(0, 0,1)},
		{glm::vec3(0.5, 1, -0.5), glm::vec3(0, 0, 1)},

		{glm::vec3(0.5, 0, -0.5), glm::vec3(1, 1, 0)},
		{glm::vec3(-0.5, 0, -0.5), glm::vec3(1, 1, 0)},
		{glm::vec3(0.5, 1, -0.5), glm::vec3(1, 1, 0)},

		//side right
		{glm::vec3(0.5, 1,  -0.5), glm::vec3(1, 0, 1)},
		{glm::vec3(0.5, 1,  0.5), glm::vec3(1, 0, 1)},
		{glm::vec3(0.5, 0, -0.5), glm::vec3(1,0, 1)},


		{glm::vec3(0.5, 0,  0.5), glm::vec3(0, 1, 1)},
		{glm::vec3(0.5, 0, -0.5), glm::vec3(0, 1, 1)},
		{glm::vec3(0.5, 1,  0.5), glm::vec3(0, 1, 1)},

		//side left
		{glm::vec3(-0.5, 0, -0.5), glm::vec3(0.2, 0.5, 0.7)},
		{glm::vec3(-0.5, 0,  0.5), glm::vec3(0.2, 0.5, 0.7)},
		{glm::vec3(-0.5, 1,  0.5), glm::vec3(0.2, 0.5, 0.7)},


		{glm::vec3(-0.5, 1,  0.5), glm::vec3(0.7, 0.5, 0.2)},
		{glm::vec3(-0.5, 1, -0.5), glm::vec3(0.7, 0.5, 0.2)},
		{glm::vec3(-0.5, 0, -0.5), glm::vec3(0.7, 0.5, 0.2)},

		//top
		{glm::vec3(0.5, 1, -0.5), glm::vec3(0.5, 0.7, 0.9)},
		{glm::vec3(-0.5, 1, -0.5), glm::vec3(0.5, 0.7, 0.9)},
		{glm::vec3(-0.5, 1,  0.5), glm::vec3(0.5, 0.7, 0.9)},

		{glm::vec3(0.5, 1,  0.5), glm::vec3(0.1, 0.3, 0.1)},
		{glm::vec3(0.5, 1, -0.5), glm::vec3(0.1, 0.3, 0.1)},
		{glm::vec3(-0.5, 1,  0.5), glm::vec3(0.1, 0.3, 0.1)},

		//front
		{glm::vec3(0.5, 1, 0.5), glm::vec3(1, 0.3, 0.6)},
		{glm::vec3(-0.5, 1, 0.5), glm::vec3(1, 0.3, 0.6)},
		{glm::vec3(-0.5, 0, 0.5), glm::vec3(1, 0.3, 0.6)},

		{glm::vec3(0.5, 0, 0.5), glm::vec3(1, 1, 1)},
		{glm::vec3(0.5, 1, 0.5), glm::vec3(1, 1, 1)},
		{glm::vec3(-0.5, 0, 0.5), glm::vec3(1, 1, 1)},


	};

	glGenVertexArrays(1, &m_vaoID);
	glBindVertexArray(m_vaoID);
	glGenBuffers(1, &m_vboID);
	glBindBuffer(GL_ARRAY_BUFFER, m_vboID);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vert), vert, GL_STATIC_DRAW);

	glEnableVertexAttribArray(0);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, sizeof(Vertex), 0);
	glEnableVertexAttribArray(1);
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, sizeof(Vertex), (void*)(sizeof(glm::vec3)));
	glBindVertexArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);


	GLuint vs_ID = loadShader(GL_VERTEX_SHADER, "myVert.vert");
	GLuint fs_ID = loadShader(GL_FRAGMENT_SHADER, "myFrag.frag");

	m_programID = glCreateProgram();

	glAttachShader(m_programID, vs_ID);
	glAttachShader(m_programID, fs_ID);

	glBindAttribLocation(m_programID, 0, "vs_in_pos");
	glBindAttribLocation(m_programID, 1, "vs_in_col");

	glLinkProgram(m_programID);

	GLint infoLogLength = 0, result = 0;

	glGetProgramiv(m_programID, GL_LINK_STATUS, &result);
	glGetProgramiv(m_programID, GL_INFO_LOG_LENGTH, &infoLogLength);
	if (GL_FALSE == result || infoLogLength != 0)
	{
		std::vector<char> VertexShaderErrorMessage(infoLogLength);
		glGetProgramInfoLog(m_programID, infoLogLength, nullptr, VertexShaderErrorMessage.data());
		std::cerr << "[glLinkProgram] Shader linking error:\n" << &VertexShaderErrorMessage[0] << std::endl;
	}

	glDeleteShader(vs_ID);
	glDeleteShader(fs_ID);

	m_loc_world = glGetUniformLocation(m_programID, "world");
	m_loc_view = glGetUniformLocation(m_programID, "view");
	m_loc_proj = glGetUniformLocation(m_programID, "proj");

	return true;
}

void CMyApp::Clean()
{
	glDeleteBuffers(1, &m_vboID);
	glDeleteVertexArrays(1, &m_vaoID);

	glDeleteProgram(m_programID);
}

void CMyApp::Update()
{

	float t = SDL_GetTicks() / 1000.f;
	m_matView = glm::lookAt(
		glm::vec3(0, 25, 25),
		glm::vec3(0, 0, 0),
		glm::vec3(0, 1, 0)
	);

}


void CMyApp::Render()
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glUseProgram(m_programID);
	glUniformMatrix4fv(m_loc_view, 1, GL_FALSE, &(m_matView[0][0]));
	glUniformMatrix4fv(m_loc_proj, 1, GL_FALSE, &(m_matProj[0][0]));
	glBindVertexArray(m_vaoID);



	float scaling = 1;
	if (spacePressed == 1) {
		scaling = (sinf(SDL_GetTicks() / 6000.f * 2.f * M_PI) * 3.f + 5.f) / 4.f;
	}

	float parabola= (sinf(SDL_GetTicks() / 8000.f * 2.f * M_PI) * 15.f + 5.f) / 2.f;


	for (int j = 0; j < numberOfShapes; ++j) {

		glm::vec3 pos = shift.at(j);
		float z = 0.15f * parabola * parabola;

		glm::mat4 transformToCorrectPosition =
			glm::translate(
				glm::vec3(
					pos.x+parabola,
					pos.y,
					pos.z+z
				)
			)
			* glm::scale(glm::vec3(1 * scaling, 1, 1));



		m_matWorld = glm::mat4(1.0f) * transformToCorrectPosition;
		glUniformMatrix4fv(m_loc_world, 1, GL_FALSE, &(m_matWorld[0][0]));
		glDrawArrays(GL_TRIANGLES, 0, 36);
		for (int i = 1; i <= 2; ++i) {
			m_matWorld = glm::translate(glm::vec3(i * 1 * scaling, 0, 0)) * transformToCorrectPosition;
			glUniformMatrix4fv(m_loc_world, 1, GL_FALSE, &(m_matWorld[0][0]));
			glDrawArrays(GL_TRIANGLES, 0, 36);

			m_matWorld = glm::translate(glm::vec3(0, i * 1, 0)) * transformToCorrectPosition;
			glUniformMatrix4fv(m_loc_world, 1, GL_FALSE, &(m_matWorld[0][0]));
			glDrawArrays(GL_TRIANGLES, 0, 36);
		}


	}

	glBindVertexArray(0);
	glUseProgram(0);
}

void CMyApp::KeyboardDown(SDL_KeyboardEvent& key)
{
	if (key.keysym.sym == SDLK_SPACE) {
		SpacePress();
	}
}

void CMyApp::KeyboardUp(SDL_KeyboardEvent& key)
{
}

void CMyApp::SpacePress() {
	std::cout << "Space pressed" << std::endl;
	spacePressed = 1;
}

void CMyApp::MouseMove(SDL_MouseMotionEvent& mouse)
{

}

void CMyApp::MouseDown(SDL_MouseButtonEvent& mouse)
{
}

void CMyApp::MouseUp(SDL_MouseButtonEvent& mouse)
{
}

void CMyApp::MouseWheel(SDL_MouseWheelEvent& wheel)
{
}

void CMyApp::Resize(int _w, int _h)
{
	glViewport(0, 0, _w, _h);

	m_matProj = glm::perspective(glm::radians(60.0f), _w / (float)_h, 0.01f, 100.0f);
}