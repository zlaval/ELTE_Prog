#version 130

// lokális változók: két tömb
vec4 positions[6] = vec4[6](
	vec4(-1, -1, 0, 1),
	vec4( 1, -1, 0, 1),
	vec4(-1,  1, 0, 1),

	vec4(-1,  1, 0, 1),
	vec4( 1, -1, 0, 1),
	vec4( 1,  1, 0, 1)
);

vec4 colors[6] = vec4[6](
	vec4(1, 0, 0, 1),
	vec4(0, 1, 0, 1),
	vec4(0, 0, 1, 1),

	vec4(0, 0, 1, 1),
	vec4(0, 1, 0, 1),
	vec4(1, 1, 1, 1)
);

// kimeneti változó: a csúcspont színe
out vec4 vs_out_col;

void main()
{
	// gl_VertexID: https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/gl_VertexID.xhtml
	gl_Position = positions[gl_VertexID];
	vs_out_col	= colors[gl_VertexID];
}